package com.luminor.task.payment.web.rest.response;

import java.sql.Timestamp;

public class CancelPaymentResponse {
    String status;
    String UUID;
    Double fee;
    Integer hours;
    Timestamp creationTime;
    Timestamp cancelTime;
    String errorMessage;

    public CancelPaymentResponse(String status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public CancelPaymentResponse(
        String status,
        String UUID,
        Double fee,
        Integer hours,
        Timestamp creationTime,
        Timestamp cancelTime
    ) {
        this.status = status;
        this.UUID = UUID;
        this.fee = fee;
        this.hours = hours;
        this.creationTime = creationTime;
        this.cancelTime = cancelTime;
    }

    public String getStatus() {
        return status;
    }

    public String getUUID() {
        return UUID;
    }

    public Double getFee() {
        return fee;
    }

    public Integer getHours() {
        return hours;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public Timestamp getCancelTime() {
        return cancelTime;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
