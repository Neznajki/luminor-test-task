package com.luminor.task.payment.db.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "payment_fee", schema = "payments")
public class PaymentFeeEntity {
    private Integer id;
    private double feeAmount;
    private double feeCoefficient;
    private Timestamp calculatedAt;
    private CanceledPaymentEntity canceledPaymentByCancelId;
    private CurrencyEntity currencyByCurrencyId;
    private PaymentTypeEntity paymentTypeByTypeId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "fee_amount")
    public double getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(double feeAmount) {
        this.feeAmount = feeAmount;
    }

    @Basic
    @Column(name = "fee_coefficient")
    public double getFeeCoefficient() {
        return feeCoefficient;
    }

    public void setFeeCoefficient(double feeCoefficient) {
        this.feeCoefficient = feeCoefficient;
    }

    @Basic
    @Column(name = "calculated_at")
    public Timestamp getCalculatedAt() {
        return calculatedAt;
    }

    public void setCalculatedAt(Timestamp calculatedAt) {
        this.calculatedAt = calculatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentFeeEntity that = (PaymentFeeEntity) o;
        return Double.compare(that.feeAmount, feeAmount) == 0 &&
            Double.compare(that.feeCoefficient, feeCoefficient) == 0 &&
            Objects.equals(id, that.id) &&
            Objects.equals(calculatedAt, that.calculatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, feeAmount, feeCoefficient, calculatedAt);
    }

    @OneToOne
    @JoinColumn(name = "cancel_id", referencedColumnName = "id", nullable = false)
    public CanceledPaymentEntity getCanceledPaymentByCancelId() {
        return canceledPaymentByCancelId;
    }

    public void setCanceledPaymentByCancelId(CanceledPaymentEntity canceledPaymentByCancelId) {
        this.canceledPaymentByCancelId = canceledPaymentByCancelId;
    }

    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id", nullable = false)
    public CurrencyEntity getCurrencyByCurrencyId() {
        return currencyByCurrencyId;
    }

    public void setCurrencyByCurrencyId(CurrencyEntity currencyByCurrencyId) {
        this.currencyByCurrencyId = currencyByCurrencyId;
    }

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    public PaymentTypeEntity getPaymentTypeByTypeId() {
        return paymentTypeByTypeId;
    }

    public void setPaymentTypeByTypeId(PaymentTypeEntity paymentTypeByTypeId) {
        this.paymentTypeByTypeId = paymentTypeByTypeId;
    }
}
