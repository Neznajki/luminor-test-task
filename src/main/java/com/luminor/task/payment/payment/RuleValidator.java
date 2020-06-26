package com.luminor.task.payment.payment;

import org.slf4j.LoggerFactory;

/** could be replaced by existing libraries **/
public class RuleValidator {
    String fieldName;
    String regex;
    String message;
    Boolean exists;
    Boolean required;

    public RuleValidator(String fieldName, String regex, String message, Boolean exists, Boolean required) {
        this.fieldName = fieldName;
        this.regex = regex;
        this.message = message;
        this.exists = exists;
        this.required = required;
    }

    public void isValid(String target) throws InvalidPaymentException {
        if (target == null || target.equals("")) {
            if (this.required) {
                throw new InvalidPaymentException(format("${fieldName} is mandatory"));
            }

            return;
        }

        if (! exists) {
            throw new InvalidPaymentException(format("field ${fieldName} not supported"));
        }

        if (! target.matches(regex)) {
            LoggerFactory.getLogger(RuleValidator.class).info(this.message);
            throw new InvalidPaymentException(format(message));
        }
    }

    public String format(String message) {
        return message.replace("${fieldName}", fieldName);
    }
}
