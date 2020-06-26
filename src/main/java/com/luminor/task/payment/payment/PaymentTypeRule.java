package com.luminor.task.payment.payment;

import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class PaymentTypeRule {
    String name;
    RuleValidator debtorIban;
    RuleValidator creditorIban;
    RuleValidator details;
    RuleValidator creditorBankBicCode;

    public PaymentTypeRule(String name, RuleValidator debtorIban, RuleValidator creditorIban, RuleValidator details, RuleValidator creditorBankBicCode) {
        this.name = name;
        this.debtorIban = debtorIban;
        this.creditorIban = creditorIban;
        this.details = details;
        this.creditorBankBicCode = creditorBankBicCode;
    }

    public void validateField(Double target) throws InvalidPaymentException {
        double minAmount = 2.00;
        if (target < minAmount) {
            throw new InvalidPaymentException(String.format("amount should be more than %4.2f", minAmount));
        }
    }

    public void validateField(String target, String fieldName) throws InvalidPaymentException {
        try {
            if (fieldName.equals("name")) {
                throw new InvalidPaymentException("field name can not be validated");
            }
            RuleValidator ruleValidator = getRuleValidatorForField(fieldName);

            ruleValidator.isValid(target);
        } catch (NoSuchFieldException e) {
            LoggerFactory.getLogger(RuleValidator.class).error(e.getMessage(), e);

            throw new InvalidPaymentException(e.getMessage());
        }
    }

    protected RuleValidator getRuleValidatorForField(String fieldName) throws NoSuchFieldException {
        return switch (fieldName) {
            case "debtorIban" -> debtorIban;
            case "creditorIban" -> creditorIban;
            case "details" -> details;
            case "creditorBankBicCode" -> creditorBankBicCode;
            default -> throw new NoSuchFieldException(fieldName);
        };
    }
}
