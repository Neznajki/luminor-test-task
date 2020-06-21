package com.luminor.task.payment.db.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "client_action", schema = "payments")
public class ClientActionEntity {
    private int id;
    private Timestamp timestamp;
    private ClientEntity clientByClientId;
    private ClientIpEntity clientIpByClientIpId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "timestamp")
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientActionEntity that = (ClientActionEntity) o;
        return id == that.id &&
            Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timestamp);
    }

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    public ClientEntity getClientByClientId() {
        return clientByClientId;
    }

    public void setClientByClientId(ClientEntity clientByClientId) {
        this.clientByClientId = clientByClientId;
    }

    @ManyToOne
    @JoinColumn(name = "client_ip_id", referencedColumnName = "id", nullable = false)
    public ClientIpEntity getClientIpByClientIpId() {
        return clientIpByClientIpId;
    }

    public void setClientIpByClientIpId(ClientIpEntity clientIpByClientIpId) {
        this.clientIpByClientIpId = clientIpByClientIpId;
    }
}
