package com.luminor.task.payment.db.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "canceled_payment", schema = "payments")
public class CanceledPaymentEntity {
    private int id;
    private Timestamp canceledTimestamp;
    private ExistingPaymentEntity existingPaymentByExistingPaymentId;
    private PaymentFeeEntity paymentFeeById;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "canceled_timestamp")
    public Timestamp getCanceledTimestamp() {
        return canceledTimestamp;
    }

    public void setCanceledTimestamp(Timestamp canceledTimestamp) {
        this.canceledTimestamp = canceledTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CanceledPaymentEntity that = (CanceledPaymentEntity) o;
        return id == that.id &&
            Objects.equals(canceledTimestamp, that.canceledTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, canceledTimestamp);
    }

    @OneToOne
    @JoinColumn(name = "existing_payment_id", referencedColumnName = "id", nullable = false)
    public ExistingPaymentEntity getExistingPaymentByExistingPaymentId() {
        return existingPaymentByExistingPaymentId;
    }

    public void setExistingPaymentByExistingPaymentId(ExistingPaymentEntity existingPaymentByExistingPaymentId) {
        this.existingPaymentByExistingPaymentId = existingPaymentByExistingPaymentId;
    }

    @OneToOne(mappedBy = "canceledPaymentByCancelId")
    public PaymentFeeEntity getPaymentFeeById() {
        return paymentFeeById;
    }

    public void setPaymentFeeById(PaymentFeeEntity paymentFeeById) {
        this.paymentFeeById = paymentFeeById;
    }
}
