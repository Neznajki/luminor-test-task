package com.luminor.task.payment.db.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "existing_payment", schema = "payments")
public class ExistingPaymentEntity {
    private int id;
    private double paymentAmount;
    private Timestamp created;
    private PaymentTypeEntity paymentTypeByPaymentTypeId;
    private ClientActionEntity clientActionByClientActionId;
    private UniqueIdEntity uniqueIdByUniqueId;
    private CurrencyEntity currencyByCurrencyId;
    private ExistingPaymentDataEntity existingPaymentDataById;
    private ClientEntity payedClientByPayedClientId;
    private CanceledPaymentEntity canceledPaymentEntity;

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
    @Column(name = "payment_amount")
    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @Basic
    @Column(name = "created")
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExistingPaymentEntity that = (ExistingPaymentEntity) o;
        return id == that.id &&
            Double.compare(that.paymentAmount, paymentAmount) == 0 &&
            Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentAmount, created);
    }

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "payment_type_id", referencedColumnName = "id", nullable = false)
    public PaymentTypeEntity getPaymentTypeByPaymentTypeId() {
        return paymentTypeByPaymentTypeId;
    }

    public void setPaymentTypeByPaymentTypeId(PaymentTypeEntity paymentTypeByPaymentTypeId) {
        this.paymentTypeByPaymentTypeId = paymentTypeByPaymentTypeId;
    }

    @ManyToOne
    @JoinColumn(name = "client_action_id", referencedColumnName = "id", nullable = false)
    public ClientActionEntity getClientActionByClientActionId() {
        return clientActionByClientActionId;
    }

    public void setClientActionByClientActionId(ClientActionEntity clientActionByClientActionId) {
        this.clientActionByClientActionId = clientActionByClientActionId;
    }

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "unique_id", referencedColumnName = "id", nullable = false)
    public UniqueIdEntity getUniqueIdByUniqueId() {
        return uniqueIdByUniqueId;
    }

    public void setUniqueIdByUniqueId(UniqueIdEntity uniqueIdByUniqueId) {
        this.uniqueIdByUniqueId = uniqueIdByUniqueId;
    }

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "currency_id", referencedColumnName = "id", nullable = false)
    public CurrencyEntity getCurrencyByCurrencyId() {
        return currencyByCurrencyId;
    }

    public void setCurrencyByCurrencyId(CurrencyEntity currencyByCurrencyId) {
        this.currencyByCurrencyId = currencyByCurrencyId;
    }

    @OneToOne(mappedBy = "existingPaymentByPaymentId")
    public ExistingPaymentDataEntity getExistingPaymentDataById() {
        return existingPaymentDataById;
    }

    public void setExistingPaymentDataById(ExistingPaymentDataEntity existingPaymentDataById) {
        this.existingPaymentDataById = existingPaymentDataById;
    }

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "payed_client_id", referencedColumnName = "id", nullable = false)
    public ClientEntity getPayedClientByPayedClientId() {
        return payedClientByPayedClientId;
    }

    public void setPayedClientByPayedClientId(ClientEntity payedClientByPayedClientId) {
        this.payedClientByPayedClientId = payedClientByPayedClientId;
    }

    @OneToOne(mappedBy = "existingPaymentByExistingPaymentId")
    public CanceledPaymentEntity getCanceledPaymentEntity() {
        return canceledPaymentEntity;
    }

    public void setCanceledPaymentEntity(CanceledPaymentEntity canceledPaymentEntity) {
        this.canceledPaymentEntity = canceledPaymentEntity;
    }
}
