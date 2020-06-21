package com.luminor.task.payment.db.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "allowed_type_currency", schema = "payments")
public class AllowedTypeCurrencyEntity {
    private int id;
    private PaymentTypeEntity paymentTypeByTypeId;
    private CurrencyEntity currencyByCurrencyId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllowedTypeCurrencyEntity that = (AllowedTypeCurrencyEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    public PaymentTypeEntity getPaymentTypeByTypeId() {
        return paymentTypeByTypeId;
    }

    public void setPaymentTypeByTypeId(PaymentTypeEntity paymentTypeByTypeId) {
        this.paymentTypeByTypeId = paymentTypeByTypeId;
    }

    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id", nullable = false)
    public CurrencyEntity getCurrencyByCurrencyId() {
        return currencyByCurrencyId;
    }

    public void setCurrencyByCurrencyId(CurrencyEntity currencyByCurrencyId) {
        this.currencyByCurrencyId = currencyByCurrencyId;
    }
}
