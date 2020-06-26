package com.luminor.task.payment.web.rest.response;

public class CreatePaymentResponse {
    String status;
    Integer newPaymentId;
    String errorMessage;
    String UUID;

    public CreatePaymentResponse(String status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public CreatePaymentResponse(String status, Integer newPaymentId, String UUID) {
        this.status = status;
        this.newPaymentId = newPaymentId;
        this.UUID = UUID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNewPaymentId() {
        return newPaymentId;
    }

    public void setNewPaymentId(Integer newPaymentId) {
        this.newPaymentId = newPaymentId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
}
