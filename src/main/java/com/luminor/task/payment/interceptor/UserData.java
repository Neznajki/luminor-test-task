package com.luminor.task.payment.interceptor;

import com.luminor.task.payment.db.entity.ClientActionEntity;
import com.luminor.task.payment.db.entity.ClientIpEntity;

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
}
