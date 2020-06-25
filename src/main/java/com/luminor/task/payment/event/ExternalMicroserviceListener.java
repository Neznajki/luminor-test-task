package com.luminor.task.payment.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luminor.task.payment.db.repository.ExternalRequestRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class ExternalMicroserviceListener {
    ExternalRequestRepository externalRequestRepository;
    ObjectMapper objectMapper;

    public ExternalMicroserviceListener(ExternalRequestRepository externalRequestRepository, ObjectMapper objectMapper) {
        this.externalRequestRepository = externalRequestRepository;
        this.objectMapper = objectMapper;
    }

    @EventListener
    public void dispatchEvent(ExternalMicroserviceEvent event) throws JsonProcessingException {
        event.getExternalRequestEntity().setResponseJson(objectMapper.writeValueAsString(event.getResponse()));
        event.getExternalRequestEntity().setResponseTime(new Timestamp(System.currentTimeMillis()));

        externalRequestRepository.saveAndFlush(event.getExternalRequestEntity());

    }
}
