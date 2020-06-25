package com.luminor.task.payment.event;

import com.luminor.task.payment.db.entity.ClientActionEntity;
import com.luminor.task.payment.db.entity.ExistingPaymentEntity;

public class PaymentCreatedEvent {
    final ExistingPaymentEntity existingPaymentEntity;
    final ClientActionEntity clientActionEntity;

    public PaymentCreatedEvent(
        ExistingPaymentEntity existingPaymentEntity,
        ClientActionEntity clientActionEntity
    ) {
        this.existingPaymentEntity = existingPaymentEntity;
        this.clientActionEntity = clientActionEntity;
    }

    public ExistingPaymentEntity getExistingPaymentEntity() {
        return existingPaymentEntity;
    }

    public ClientActionEntity getClientActionEntity() {
        return clientActionEntity;
    }
}
