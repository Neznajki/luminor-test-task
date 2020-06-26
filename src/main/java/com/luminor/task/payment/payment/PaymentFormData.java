package com.luminor.task.payment.payment;

public class PaymentFormData {
    Integer allowedTypeCurrencyEntityId;
    String debtorIban;
    String creditorIban;
    String details;
    String creditorBankBicCode;
    Double amount;

    public Integer getAllowedTypeCurrencyEntityId() {
        return allowedTypeCurrencyEntityId;
    }

    public void setAllowedTypeCurrencyEntityId(Integer allowedTypeCurrencyEntityId) {
        this.allowedTypeCurrencyEntityId = allowedTypeCurrencyEntityId;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
