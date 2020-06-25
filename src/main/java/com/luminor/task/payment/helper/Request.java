package com.luminor.task.payment.helper;

import com.luminor.task.payment.db.entity.ClientActionEntity;
import com.luminor.task.payment.interceptor.UserData;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class Request {
    public static ClientActionEntity getCurrentUserAction() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
            Object result = request.getAttribute(UserData.ATTRIBUTE_INDEX);

            if (result == null) {
                throw new RuntimeException("could not find client action data");
            }

            if (! (result instanceof UserData)) {
                throw new RuntimeException(String.format("client action should be instance of %s but got %s", ClientActionEntity.class, result.getClass()));
            }

            return ((UserData) result).getClientActionEntity();
        }

        throw new RuntimeException("this is not http request !!");
    }
}
