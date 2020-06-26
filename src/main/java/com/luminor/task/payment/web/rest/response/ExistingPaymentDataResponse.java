package com.luminor.task.payment.web.rest.response;

import com.luminor.task.payment.db.entity.ExistingPaymentEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

public class ExistingPaymentDataResponse {
    public static Collection<ExistingPaymentDataResponse> fromEntityCollection(Collection<ExistingPaymentEntity> existingPaymentEntities) {
        Collection<ExistingPaymentDataResponse> result = new ArrayList<>(existingPaymentEntities.size());

        for (ExistingPaymentEntity entity: existingPaymentEntities) {
            ExistingPaymentDataResponse response = fromExistingEntity(entity);

            result.add(response);
        }

        return result;
    }

    public static ExistingPaymentDataResponse fromExistingEntity(ExistingPaymentEntity entity) {
        ExistingPaymentDataResponse response = new ExistingPaymentDataResponse();

        response.setUUID(entity.getUniqueIdByUniqueId().getHashValue());
        response.setType(entity.getPaymentTypeByPaymentTypeId().getTypeName());
        response.setCreated(entity.getCreated());
        response.setPayment(entity.getPaymentAmount());
        response.setCanceled(entity.getCanceledPaymentEntity() != null);
        if (response.getCanceled()) {
            response.setCancelFee(entity.getCanceledPaymentEntity().getPaymentFeeById().getFeeAmount());
            response.setCancelTime(entity.getCanceledPaymentEntity().getCanceledTimestamp());
        }

        return response;
    }

    String UUID;
    String type;
    Double payment;
    Timestamp created;
    Timestamp cancelTime;
    Boolean isCanceled;
    Double cancelFee;

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Timestamp cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Boolean getCanceled() {
        return isCanceled;
    }

    public void setCanceled(Boolean canceled) {
        isCanceled = canceled;
    }

    public Double getCancelFee() {
        return cancelFee;
    }

    public void setCancelFee(Double cancelFee) {
        this.cancelFee = cancelFee;
    }
}
