package com.luminor.task.payment.interceptor;

import com.luminor.task.payment.db.entity.ClientActionEntity;
import com.luminor.task.payment.db.entity.ClientIpEntity;

import java.util.Objects;

public class UserData {
    private ClientIpEntity clientIpEntity;
    private ClientActionEntity clientActionEntity;

    public ClientIpEntity getClientIpEntity() {
        return clientIpEntity;
    }

    public void setClientIpEntity(ClientIpEntity clientIpEntity) {
        this.clientIpEntity = clientIpEntity;
    }

    public ClientActionEntity getClientActionEntity() {
        return clientActionEntity;
    }

    public void setClientActionEntity(ClientActionEntity clientActionEntity) {
        this.clientActionEntity = clientActionEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return Objects.equals(clientIpEntity, userData.clientIpEntity) &&
            Objects.equals(clientActionEntity, userData.clientActionEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientIpEntity, clientActionEntity);
    }
}
