package com.luminor.task.payment.external.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luminor.task.payment.db.entity.ClientActionEntity;
import com.luminor.task.payment.db.entity.ExternalRequestEntity;
import com.luminor.task.payment.db.repository.ExternalRequestRepository;
import com.luminor.task.payment.event.ExternalMicroserviceEvent;
import com.luminor.task.payment.event.PaymentCanceledEvent;
import com.luminor.task.payment.event.PaymentCreatedEvent;
import com.luminor.task.payment.external.api.request.ExternalMicroServiceRequest;
import com.luminor.task.payment.external.api.response.ExternalMicroServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ExternalMicroServicePaymentNotifier {
    ApplicationEventPublisher publisher;
    ExternalRequestRepository externalRequestRepository;
    ObjectMapper objectMapper;

    @Autowired
    public ExternalMicroServicePaymentNotifier(
        ApplicationEventPublisher publisher,
        ExternalRequestRepository externalRequestRepository,
        ObjectMapper objectMapper
    ) {
        this.publisher = publisher;
        this.externalRequestRepository = externalRequestRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * here would we have external service notification logic
     */
    public boolean notifyEvent(PaymentCanceledEvent paymentCreatedEvent) throws JsonProcessingException {
        ExternalMicroServiceRequest request = new ExternalMicroServiceRequest(
            paymentCreatedEvent.getCanceledPaymentEntity().getExistingPaymentByExistingPaymentId().getUniqueIdByUniqueId().getHashValue(),
            paymentCreatedEvent.getCanceledPaymentEntity().getPaymentFeeById().getFeeAmount()
        );

        return sendNotification(request, paymentCreatedEvent.getClientActionEntity());
    }

    /**
     * here would we have external service notification logic
     */
    public boolean notifyEvent(PaymentCreatedEvent paymentCreatedEvent) throws JsonProcessingException {
        ExternalMicroServiceRequest request = new ExternalMicroServiceRequest(
            paymentCreatedEvent.getExistingPaymentEntity().getUniqueIdByUniqueId().getHashValue()
        );

        return sendNotification(request, paymentCreatedEvent.getClientActionEntity());
    }

    protected boolean sendNotification(ExternalMicroServiceRequest request, ClientActionEntity clientActionEntity) throws JsonProcessingException {
        ExternalRequestEntity externalRequestEntity = new ExternalRequestEntity();
        externalRequestEntity.setRequestJson(objectMapper.writeValueAsString(request));
        externalRequestEntity.setRequestTime(new Timestamp(System.currentTimeMillis()));
        externalRequestEntity.setClientActionByActionId(clientActionEntity);

        externalRequestRepository.saveAndFlush(externalRequestEntity);
//       response = client.makeExternalRequest(request);
        ExternalMicroServiceResponse response = new ExternalMicroServiceResponse("success");
        this.publisher.publishEvent(new ExternalMicroserviceEvent(request, response, externalRequestEntity));

        return true;
    }
}
