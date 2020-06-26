package com.luminor.task.payment.web.rest.request;

public class CancelPaymentRequest {
    public static CancelPaymentRequest factory(String UUID) {
        CancelPaymentRequest result = new CancelPaymentRequest();

        result.setUUID(UUID);

        return result;
    }

    public String UUID;

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
}
