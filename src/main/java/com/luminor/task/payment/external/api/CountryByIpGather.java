package com.luminor.task.payment.external.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luminor.task.payment.external.api.response.CountryByIpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

@Service
public class CountryByIpGather {
    ObjectMapper objectMapper;

    @Autowired
    public CountryByIpGather(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /** see no reasons on increase complexity to logic by adding batch requests */
    public String getCountryCode(String ipAddress) throws IOException {
        URL url = new URL("http://ip-api.com/json/".concat(ipAddress));
        CountryByIpResponse result = objectMapper.readValue(url, CountryByIpResponse.class);

        if (result.status.equals("success") && result.countryCode.matches("[A-Za-z]+")) {
            return result.countryCode;
        }

        return null;
    }
}
