package com.luminor.task.payment.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor implements HandlerInterceptor {
    ClientMetaDataSaverImpl clientMetaDataSaver;
    Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

    @Autowired
    public RequestInterceptor(ClientMetaDataSaverImpl clientMetaDataSaver) {
        this.clientMetaDataSaver = clientMetaDataSaver;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //would be cool to add something like route visits history table, in case we need to monitor visitors. to find suitable component or make logic will take some time so just stick to task requirements.
        if (request.getRequestURI().matches("^/?resources/.*")) {
            return true;
        }

        request.setAttribute("client.meta.data.entity", clientMetaDataSaver.createRequestUserMetaData(request));

        return true;
    }
}
