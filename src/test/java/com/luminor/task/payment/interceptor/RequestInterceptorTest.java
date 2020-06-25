package com.luminor.task.payment.interceptor;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RequestInterceptorTest {
    @MockBean
    RequestInterceptor requestInterceptor;
    @MockBean
    ClientMetaDataSaverImpl clientMetaDataSaver;

    public static Object[][] dataProvider() {
        return new Object[][]{
            {"coolMapping", Mockito.mock(UserData.class)},
            {"resources/js", null},
            {"/resources/js", null},
            {"/resources/", null},
            {"resourcesCoolPlace", Mockito.mock(UserData.class)},
        };
    }

    @ParameterizedTest
    @MethodSource(value = "dataProvider")
    public void preHandleTest(String uri, UserData indexData) {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        ReflectionTestUtils.setField(requestInterceptor, "clientMetaDataSaver", clientMetaDataSaver);

        HttpServletResponse response = mock(HttpServletResponse.class);
        Object handler = mock(Object.class);
        when(request.getRequestURI()).thenReturn(uri);
        when(requestInterceptor.preHandle(
            request,
            response,
            handler
        )).thenCallRealMethod();

        if (indexData != null) {
            when(clientMetaDataSaver.createRequestUserMetaData(request)).thenReturn(indexData);
            doNothing().when(request).setAttribute("client.meta.data.entity", indexData);
        }

        assertTrue(requestInterceptor.preHandle(request, response, handler));

        if (indexData == null) {
            verify(request, never()).setAttribute(any(), any());
        }
        //        //would be cool to add something like route visits history table, in case we need to monitor visitors. to find suitable component or make logic will take some time so just stick to task requirements.
        //        if (request.getRequestURI().matches("^/?resources/.*")) {
        //            return true;
        //        }
        //
        //        request.setAttribute("client.meta.data.entity", clientMetaDataSaver.createRequestUserMetaData(request));
        //
        //        return true;
    }
}
