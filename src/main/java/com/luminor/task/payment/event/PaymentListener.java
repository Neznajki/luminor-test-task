package com.luminor.task.payment.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.luminor.task.payment.external.api.ExternalMicroServicePaymentNotifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {
    ExternalMicroServicePaymentNotifier externalMicroServicePaymentNotifier;

    @Autowired
    public PaymentListener(ExternalMicroServicePaymentNotifier externalMicroServicePaymentNotifier) {
        this.externalMicroServicePaymentNotifier = externalMicroServicePaymentNotifier;
    }

    @EventListener
    public void handlePaymentCreatedEvent(PaymentCreatedEvent paymentCreatedEvent) throws JsonProcessingException {
        externalMicroServicePaymentNotifier.notifyEvent(paymentCreatedEvent);
    }
}
