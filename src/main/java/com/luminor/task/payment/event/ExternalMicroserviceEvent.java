package com.luminor.task.payment.event;

import com.luminor.task.payment.db.entity.ExternalRequestEntity;
import com.luminor.task.payment.external.api.request.ExternalMicroServiceRequest;
import com.luminor.task.payment.external.api.response.ExternalMicroServiceResponse;

public class ExternalMicroserviceEvent {
    final ExternalMicroServiceRequest request;
    final ExternalMicroServiceResponse response;
    final ExternalRequestEntity externalRequestEntity;

    public ExternalMicroserviceEvent(
        ExternalMicroServiceRequest request,
        ExternalMicroServiceResponse response,
        ExternalRequestEntity externalRequestEntity
    ) {
        this.request = request;
        this.response = response;
        this.externalRequestEntity = externalRequestEntity;
    }

    public ExternalMicroServiceRequest getRequest() {
        return request;
    }

    public ExternalMicroServiceResponse getResponse() {
        return response;
    }

    public ExternalRequestEntity getExternalRequestEntity() {
        return externalRequestEntity;
    }
}
