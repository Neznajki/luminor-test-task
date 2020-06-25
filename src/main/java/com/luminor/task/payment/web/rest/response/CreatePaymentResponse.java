package com.luminor.task.payment.web.rest.response;

public class CreatePaymentResponse {
    String status;
    Integer newPaymentId;
    String errorMessage;

    public CreatePaymentResponse(String status, Integer newPaymentId, String errorMessage) {
        this.status = status;
        this.newPaymentId = newPaymentId;
        this.errorMessage = errorMessage;
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
}
