package com.luminor.task.payment.external.api.response;

public class ExternalMicroServiceResponse {
    String status;

    public ExternalMicroServiceResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
