package com.luminor.task.payment.payment;

import com.luminor.task.payment.db.entity.ExistingPaymentEntity;
import com.luminor.task.payment.helper.Time;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Service
public class CancelFeeServiceImpl {

    public Boolean canCancelPayment(ExistingPaymentEntity paymentEntity) {
        if (paymentEntity.getCanceledPaymentEntity() != null) {
            return false;
        }

        SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");

        if (dateOnly.format(paymentEntity.getCreated().getTime()).equals(dateOnly.format(System.currentTimeMillis()))) {
            return true;
        }

        if (getCalculatedFee(paymentEntity) == null) {
            return false;
        }

        return false;
    }

    public Double getCalculatedFee(ExistingPaymentEntity paymentEntity) {
        Double totalFee = Time.diffTimeHours(new Timestamp(System.currentTimeMillis()), paymentEntity.getCreated()) * paymentEntity.getPaymentTypeByPaymentTypeId().getFeeCoefficient();

        if (totalFee > paymentEntity.getPaymentAmount()) {
            //you can't cancel payment if your fee is higher than payment
            return null;
        }

        return totalFee;
    }
}
