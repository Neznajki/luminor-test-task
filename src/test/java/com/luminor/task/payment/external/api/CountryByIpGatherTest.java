package com.luminor.task.payment.external.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.luminor.task.payment.external.api.response.CountryByIpResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CountryByIpGatherTest {
    @MockBean
    CountryByIpGather countryByIpGather;
    @MockBean
    ObjectMapper objectMapper;

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void getCountryCodeTest(Boolean successResponse) throws IOException {
        ReflectionTestUtils.setField(countryByIpGather, "objectMapper", objectMapper);
        URL urlMock = new URL("http://unit test url");

        CountryByIpResponse responseMock = new CountryByIpResponse();
        responseMock.countryCode = "test country code";
        String ipMock = "test ip";
        when(countryByIpGather.getCountryCode(ipMock)).thenCallRealMethod();
        when(countryByIpGather.getUrlForIp(ipMock)).thenReturn(urlMock);
        when(objectMapper.readValue(urlMock, CountryByIpResponse.class)).thenReturn(responseMock);
        when(countryByIpGather.isApiResponseCountryCodeValid(responseMock)).thenReturn(successResponse);

        String response = countryByIpGather.getCountryCode(ipMock);

        if (successResponse) {
            assertEquals(responseMock.countryCode, response);
        } else {
            assertNull(response);
        }
    }

    public static Object[][] dataProviderForIsApiResponseCountryCodeValidTest() {
        return new Object[][]{
            {/* status */"success",/* countryCode */"LL", /* result */true},
            {/* status */"fail",/* countryCode */"LL", /* result */false},
            {/* status */"fail",/* countryCode */"Some Country", /* result */false},
            {/* status */"success",/* countryCode */"Some Country", /* result */false},
        };
    }

    @ParameterizedTest
    @MethodSource(value = "dataProviderForIsApiResponseCountryCodeValidTest")
    public void isApiResponseCountryCodeValidTest(String status, String countryCode, boolean result) {
        CountryByIpResponse response = new CountryByIpResponse();

        response.status = status;
        response.countryCode = countryCode;

        when(countryByIpGather.isApiResponseCountryCodeValid(response)).thenCallRealMethod();

        assertEquals(result, countryByIpGather.isApiResponseCountryCodeValid(response));
    }

    @Test
    public void getUrlForIpTest() throws MalformedURLException {
        String ipMock = "test ip";

        when(countryByIpGather.getUrlForIp(ipMock)).thenCallRealMethod();

        assertEquals(new URL(CountryByIpGather.HTTP_IP_API_COM_JSON.concat(ipMock)), countryByIpGather.getUrlForIp(ipMock));
    }
}
