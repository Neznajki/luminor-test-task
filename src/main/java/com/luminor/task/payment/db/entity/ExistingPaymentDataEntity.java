package com.luminor.task.payment.db.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "existing_payment_data", schema = "payments")
public class ExistingPaymentDataEntity {
    private int id;
    private String debtorIban;
    private String creditorIban;
    private String details;
    private String creditorBankBicCode;
    private ExistingPaymentEntity existingPaymentByPaymentId;

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
    @Column(name = "debtor_iban")
    public String getDebtorIban() {
        return debtorIban;
    }

    public void setDebtorIban(String debtorIban) {
        this.debtorIban = debtorIban;
    }

    @Basic
    @Column(name = "creditor_iban")
    public String getCreditorIban() {
        return creditorIban;
    }

    public void setCreditorIban(String creditorIban) {
        this.creditorIban = creditorIban;
    }

    @Basic
    @Column(name = "details")
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Basic
    @Column(name = "creditor_bank_bic_code")
    public String getCreditorBankBicCode() {
        return creditorBankBicCode;
    }

    public void setCreditorBankBicCode(String creditorBankBicCode) {
        this.creditorBankBicCode = creditorBankBicCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExistingPaymentDataEntity that = (ExistingPaymentDataEntity) o;
        return id == that.id &&
            Objects.equals(debtorIban, that.debtorIban) &&
            Objects.equals(creditorIban, that.creditorIban) &&
            Objects.equals(details, that.details) &&
            Objects.equals(creditorBankBicCode, that.creditorBankBicCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, debtorIban, creditorIban, details, creditorBankBicCode);
    }

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    public ExistingPaymentEntity getExistingPaymentByPaymentId() {
        return existingPaymentByPaymentId;
    }

    public void setExistingPaymentByPaymentId(ExistingPaymentEntity existingPaymentByPaymentId) {
        this.existingPaymentByPaymentId = existingPaymentByPaymentId;
    }


}
