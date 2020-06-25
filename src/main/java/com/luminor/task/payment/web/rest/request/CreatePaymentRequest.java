package com.luminor.task.payment.web.rest.request;

import com.luminor.task.payment.db.entity.AllowedTypeCurrencyEntity;

public class CreatePaymentRequest {
    String clientName;
    Double amount;
    Integer allowedTypeCurrencyId;
    String debtorIban;
    String creditorIban;
    String details;
    String creditorBankBicCode;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getAllowedTypeCurrencyId() {
        return allowedTypeCurrencyId;
    }

    public void setAllowedTypeCurrencyId(Integer allowedTypeCurrencyId) {
        this.allowedTypeCurrencyId = allowedTypeCurrencyId;
    }

    public String getDebtorIban() {
        return debtorIban;
    }

    public void setDebtorIban(String debtorIban) {
        this.debtorIban = debtorIban;
    }

    public String getCreditorIban() {
        return creditorIban;
    }

    public void setCreditorIban(String creditorIban) {
        this.creditorIban = creditorIban;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCreditorBankBicCode() {
        return creditorBankBicCode;
    }

    public void setCreditorBankBicCode(String creditorBankBicCode) {
        this.creditorBankBicCode = creditorBankBicCode;
    }
}
