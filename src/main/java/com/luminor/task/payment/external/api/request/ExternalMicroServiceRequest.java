package com.luminor.task.payment.external.api.request;

public class ExternalMicroServiceRequest {
    String paymentId;
    Boolean isCanceled;
    Boolean isCreated;
    Double cancelFee;

    public ExternalMicroServiceRequest(String paymentId) {
        this.isCanceled = false;
        this.isCreated = true;
    }

    public ExternalMicroServiceRequest(String paymentId, Double cancelFee) {
        this.paymentId = paymentId;
        this.isCanceled = true;
        this.isCreated = false;
        this.cancelFee = cancelFee;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Boolean getCanceled() {
        return isCanceled;
    }

    public void setCanceled(Boolean canceled) {
        isCanceled = canceled;
    }

    public Boolean getCreated() {
        return isCreated;
    }

    public void setCreated(Boolean created) {
        isCreated = created;
    }

    public Double getCancelFee() {
        return cancelFee;
    }

    public void setCancelFee(Double cancelFee) {
        this.cancelFee = cancelFee;
    }
}
