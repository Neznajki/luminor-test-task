package com.luminor.task.payment.db.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "external_request", schema = "payments")
public class ExternalRequestEntity {
    private int id;
    private String requestJson;
    private String responseJson;
    private Timestamp requestTime;
    private Timestamp responseTime;
    private ClientActionEntity clientActionByActionId;

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
    @Column(name = "request_json")
    public String getRequestJson() {
        return requestJson;
    }

    public void setRequestJson(String requestJson) {
        this.requestJson = requestJson;
    }

    @Basic
    @Column(name = "response_json")
    public String getResponseJson() {
        return responseJson;
    }

    public void setResponseJson(String responseJson) {
        this.responseJson = responseJson;
    }

    @Basic
    @Column(name = "request_time")
    public Timestamp getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Timestamp requestTime) {
        this.requestTime = requestTime;
    }

    @Basic
    @Column(name = "response_time")
    public Timestamp getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Timestamp responseTime) {
        this.responseTime = responseTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExternalRequestEntity that = (ExternalRequestEntity) o;
        return id == that.id &&
            Objects.equals(requestJson, that.requestJson) &&
            Objects.equals(responseJson, that.responseJson) &&
            Objects.equals(requestTime, that.requestTime) &&
            Objects.equals(responseTime, that.responseTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, requestJson, responseJson, requestTime, responseTime);
    }

    @ManyToOne
    @JoinColumn(name = "action_id", referencedColumnName = "id", nullable = false)
    public ClientActionEntity getClientActionByActionId() {
        return clientActionByActionId;
    }

    public void setClientActionByActionId(ClientActionEntity clientActionByActionId) {
        this.clientActionByActionId = clientActionByActionId;
    }
}
