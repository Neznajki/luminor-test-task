package com.luminor.task.payment.event;

import com.luminor.task.payment.db.entity.CanceledPaymentEntity;
import com.luminor.task.payment.db.entity.ClientActionEntity;

public class PaymentCanceledEvent {
    final ClientActionEntity clientActionEntity;
    final CanceledPaymentEntity canceledPaymentEntity;

    public PaymentCanceledEvent(ClientActionEntity clientActionEntity, CanceledPaymentEntity canceledPaymentEntity) {
        this.clientActionEntity = clientActionEntity;
        this.canceledPaymentEntity = canceledPaymentEntity;
    }

    public ClientActionEntity getClientActionEntity() {
        return clientActionEntity;
    }

    public CanceledPaymentEntity getCanceledPaymentEntity() {
        return canceledPaymentEntity;
    }
}
