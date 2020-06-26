package com.luminor.task.payment.payment;

import com.luminor.task.payment.web.rest.request.CreatePaymentRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ValidatorCollection {
    Map<String, PaymentTypeRule> validationCollection;
//### Client should be able to create payment of one of 3 types - TYPE1, TYPE2, TYPE3. Fields 'amount' (positive decimal), 'currency' (EUR or USD), 'debtor_iban' and 'creditor_iban' (texts) are mandatory for all types.
//#### Additional type-specific requirements:
//* TYPE1 is only applicable for EUR payments, has additional field 'details' (text) which is mandatory;
//* TYPE2 is only applicable for USD payments, has additional field ‘details’ (text) which is optional.
//* TYPE3 is applicable for payments in both EUR and USD currency, has additional field for creditor bank BIC code (text) which is mandatory.
    public ValidatorCollection() {
        validationCollection = new HashMap<>();
        validationCollection.put("TYPE1", new PaymentTypeRule(
            "TYPE1",
            new RuleValidator("debtor_iban", "(?i)^[a-z\\s]{4,}$", "${fieldName} should have more than 4 symbols and contain only alphabetical letters and spaces", true, true),
            new RuleValidator("creditor_iban", "(?i)^[a-z\\s]{4,}$", "${fieldName} should have more than 4 symbols and contain only alphabetical letters and spaces", true, true),
            new RuleValidator("details", "(?i)^[a-z\\s]{4,}$", "${fieldName} should have more than 4 symbols and contain only alphabetical letters and spaces", true, true),
            new RuleValidator("creditorBankBicCode", null, "${fieldName} should have more than 4 symbols and contain only alphabetical letters and spaces", false, false)
        ));
        validationCollection.put("TYPE2", new PaymentTypeRule(
            "TYPE2",
            new RuleValidator("debtor_iban", "(?i)^[a-z\\s]{4,}$", "${fieldName} should have more than 4 symbols and contain only alphabetical letters and spaces", true, true),
            new RuleValidator("creditor_iban", "(?i)^[a-z\\s]{4,}$", "${fieldName} should have more than 4 symbols and contain only alphabetical letters and spaces", true, true),
            new RuleValidator("details", "(?i)^[a-z\\s]*$", "${fieldName} should have more than 4 symbols and contain only alphabetical letters and spaces", true, false),
            new RuleValidator("creditorBankBicCode", "(?i)^[a-z\\s]{4,}$", "${fieldName} should have more than 4 symbols and contain only alphabetical letters and spaces", false, false)
        ));
        validationCollection.put("TYPE3", new PaymentTypeRule(
            "TYPE3",
            new RuleValidator("debtor_iban", "(?i)^[a-z\\s]{4,}$", "${fieldName} should have more than 4 symbols and contain only alphabetical letters and spaces", true, true),
            new RuleValidator("creditor_iban", "(?i)^[a-z\\s]{4,}$", "${fieldName} should have more than 4 symbols and contain only alphabetical letters and spaces", true, true),
            new RuleValidator("details", null, "${fieldName} should have more than 4 symbols and contain only alphabetical letters and spaces", false, false),
            new RuleValidator("creditorBankBicCode", "(?i)^[a-z\\s]{4,}$", "${fieldName} should have more than 4 symbols and contain only alphabetical letters and spaces", true, true)
        ));
    }

    public void validate(String name, CreatePaymentRequest paymentRequest) throws InvalidPaymentException {
        PaymentTypeRule rule = getPaymentRule(name);

        rule.validateField(paymentRequest.getDebtorIban(), "debtorIban");
        rule.validateField(paymentRequest.getCreditorIban(), "creditorIban");
        rule.validateField(paymentRequest.getDetails(), "details");
        rule.validateField(paymentRequest.getCreditorBankBicCode(), "creditorBankBicCode");
        rule.validateField(paymentRequest.getAmount());
    }

    protected PaymentTypeRule getPaymentRule(String name) throws InvalidPaymentException {
        if (! this.validationCollection.containsKey(name.intern())) {
            throw new InvalidPaymentException(String.format("payment type %s not supported", name));
        }

        return this.validationCollection.get(name);
    }
}
