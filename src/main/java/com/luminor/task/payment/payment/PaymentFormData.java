package com.luminor.task.payment.payment;

import com.luminor.task.payment.db.entity.AllowedTypeCurrencyEntity;

public class PaymentFormData {
    private AllowedTypeCurrencyEntity allowedTypeCurrencyEntity;
    private Double amount;

    public AllowedTypeCurrencyEntity getAllowedTypeCurrencyEntity() {
        return allowedTypeCurrencyEntity;
    }

    public void setAllowedTypeCurrencyEntity(AllowedTypeCurrencyEntity allowedTypeCurrencyEntity) {
        this.allowedTypeCurrencyEntity = allowedTypeCurrencyEntity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
