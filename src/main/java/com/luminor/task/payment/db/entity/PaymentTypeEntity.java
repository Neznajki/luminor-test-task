package com.luminor.task.payment.db.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "payment_type", schema = "payments")
public class PaymentTypeEntity {
    private int id;
    private String typeName;
    private double feeCoefficient;
    private Collection<AllowedTypeCurrencyEntity> allowedTypeCurrenciesById;
    private Collection<PaymentFeeEntity> paymentFeesById;

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
    @Column(name = "type_name")
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Basic
    @Column(name = "fee_coefficient")
    public double getFeeCoefficient() {
        return feeCoefficient;
    }

    public void setFeeCoefficient(double feeCoefficient) {
        this.feeCoefficient = feeCoefficient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentTypeEntity that = (PaymentTypeEntity) o;
        return id == that.id &&
            Double.compare(that.feeCoefficient, feeCoefficient) == 0 &&
            Objects.equals(typeName, that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeName, feeCoefficient);
    }

    @OneToMany(mappedBy = "paymentTypeByTypeId")
    public Collection<AllowedTypeCurrencyEntity> getAllowedTypeCurrenciesById() {
        return allowedTypeCurrenciesById;
    }

    public void setAllowedTypeCurrenciesById(Collection<AllowedTypeCurrencyEntity> allowedTypeCurrenciesById) {
        this.allowedTypeCurrenciesById = allowedTypeCurrenciesById;
    }

    @OneToMany(mappedBy = "paymentTypeByTypeId")
    public Collection<PaymentFeeEntity> getPaymentFeesById() {
        return paymentFeesById;
    }

    public void setPaymentFeesById(Collection<PaymentFeeEntity> paymentFeesById) {
        this.paymentFeesById = paymentFeesById;
    }

    @Override
    public String toString() {
        return String.format("%s <%4.2f>", typeName, feeCoefficient);
    }
}
