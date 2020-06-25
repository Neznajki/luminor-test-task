package com.luminor.task.payment.db.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "currency_stamp", schema = "payments")
public class CurrencyStampEntity {
    private Integer id;
    private Double currencyRate;
    private CurrencyEntity currencyByMainCurrencyId;
    private CurrencyEntity currencyByAdditionalCurrencyId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "currency_rate")
    public Double getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(Double currencyRate) {
        this.currencyRate = currencyRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyStampEntity that = (CurrencyStampEntity) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(currencyRate, that.currencyRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currencyRate);
    }

    @ManyToOne
    @JoinColumn(name = "main_currency_id", referencedColumnName = "id", nullable = false)
    public CurrencyEntity getCurrencyByMainCurrencyId() {
        return currencyByMainCurrencyId;
    }

    public void setCurrencyByMainCurrencyId(CurrencyEntity currencyByMainCurrencyId) {
        this.currencyByMainCurrencyId = currencyByMainCurrencyId;
    }

    @ManyToOne
    @JoinColumn(name = "additional_currency_id", referencedColumnName = "id")
    public CurrencyEntity getCurrencyByAdditionalCurrencyId() {
        return currencyByAdditionalCurrencyId;
    }

    public void setCurrencyByAdditionalCurrencyId(CurrencyEntity currencyByAdditionalCurrencyId) {
        this.currencyByAdditionalCurrencyId = currencyByAdditionalCurrencyId;
    }
}
