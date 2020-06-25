package com.luminor.task.payment.external.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luminor.task.payment.external.api.response.CountryByIpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class CountryByIpGather {
    public static final String HTTP_IP_API_COM_JSON = "http://ip-api.com/json/";
    ObjectMapper objectMapper;

    @Autowired
    public CountryByIpGather(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /** see no reasons on increase complexity to logic by adding batch requests */
    public String getCountryCode(String ipAddress) throws IOException {
        URL url = getUrlForIp(ipAddress);
        CountryByIpResponse result = objectMapper.readValue(url, CountryByIpResponse.class);

        if (isApiResponseCountryCodeValid(result)) {
            return result.countryCode;
        }

        return null;
    }

    protected boolean isApiResponseCountryCodeValid(CountryByIpResponse result) {
        return result.status.equals("success") && result.countryCode.matches("[A-Za-z]+");
    }

    protected URL getUrlForIp(String ipAddress) throws MalformedURLException {
        return new URL(HTTP_IP_API_COM_JSON.concat(ipAddress));
    }
}
