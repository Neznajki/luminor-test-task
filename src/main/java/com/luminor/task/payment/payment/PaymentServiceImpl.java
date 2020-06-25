package com.luminor.task.payment.payment;

import com.luminor.task.payment.db.entity.*;
import com.luminor.task.payment.db.repository.AllowedTypeCurrencyRepository;
import com.luminor.task.payment.db.repository.ClientRepository;
import com.luminor.task.payment.db.repository.ExistingPaymentRepository;
import com.luminor.task.payment.event.PaymentCreatedEvent;
import com.luminor.task.payment.helper.Request;
import com.luminor.task.payment.web.rest.request.CreatePaymentRequest;
import com.luminor.task.payment.web.rest.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class PaymentServiceImpl {
    ClientRepository clientRepository;
    AllowedTypeCurrencyRepository allowedTypeCurrencyRepository;
    ExistingPaymentRepository existingPaymentRepository;
    PaymentDataServiceImpl paymentDataService;
    UniqueIdServiceImpl uniqueIdService;
    CurrencyStampServiceImpl currencyStampService;
    ApplicationEventPublisher publisher;

    @Autowired
    public PaymentServiceImpl(
        ClientRepository clientRepository,
        AllowedTypeCurrencyRepository allowedTypeCurrencyRepository,
        ExistingPaymentRepository existingPaymentRepository,
        PaymentDataServiceImpl paymentDataService,
        UniqueIdServiceImpl uniqueIdService,
        CurrencyStampServiceImpl currencyStampService,
        ApplicationEventPublisher publisher
    ) {
        this.clientRepository = clientRepository;
        this.allowedTypeCurrencyRepository = allowedTypeCurrencyRepository;
        this.existingPaymentRepository = existingPaymentRepository;
        this.paymentDataService = paymentDataService;
        this.uniqueIdService = uniqueIdService;
        this.currencyStampService = currencyStampService;
        this.publisher = publisher;
    }

    public CreatePaymentResponse createPayment(CreatePaymentRequest paymentRequest)
    {
        ClientEntity clientEntity = clientRepository.findByLogin(paymentRequest.getClientName());
        if (clientEntity == null) {
            return new CreatePaymentResponse("fail", null, String.format("%s user not found", paymentRequest.getClientName()));
        }
        Optional<AllowedTypeCurrencyEntity> allowedTypeCurrencyEntityOptional = allowedTypeCurrencyRepository.findById(paymentRequest.getAllowedTypeCurrencyId());
        if (allowedTypeCurrencyEntityOptional.isEmpty()) {
            return new CreatePaymentResponse("fail", null, String.format("%d allowed type currency not found", paymentRequest.getAllowedTypeCurrencyId()));
        }
        AllowedTypeCurrencyEntity allowedTypeCurrencyEntity = allowedTypeCurrencyEntityOptional.get();

        try {
            paymentDataService.validatePaymentType(paymentRequest, allowedTypeCurrencyEntity);
        } catch (InvalidPaymentException exception) {
            return new CreatePaymentResponse("fail", null, exception.getMessage());
        }

        ClientActionEntity currentUserAction = Request.getCurrentUserAction();
        UniqueIdEntity newUniqueEntity = uniqueIdService.getNewUniqueEntity();

        ExistingPaymentEntity existingPaymentEntity = this.createExistingPaymentEntity(
            clientEntity,
            allowedTypeCurrencyEntity.getCurrencyByCurrencyId(),
            allowedTypeCurrencyEntity.getPaymentTypeByTypeId(),
            paymentRequest.getAmount(),
            currentUserAction,
            newUniqueEntity
        );

        publisher.publishEvent(new PaymentCreatedEvent(existingPaymentEntity, currentUserAction));

        return new CreatePaymentResponse("success", 1, null);
    }

    @Transactional
    public ExistingPaymentEntity createExistingPaymentEntity(
        ClientEntity clientEntity,
        CurrencyEntity currencyEntity,
        PaymentTypeEntity paymentTypeEntity,
        Double amount,
        ClientActionEntity currentUserAction,
        UniqueIdEntity newUniqueEntity
    ) {
        currencyStampService.createCurrencyStamp();
        ExistingPaymentEntity existingPaymentEntity = new ExistingPaymentEntity();

        existingPaymentEntity.setClientActionByClientActionId(currentUserAction);
        existingPaymentEntity.setPayedClientByPayedClientId(clientEntity);
        existingPaymentEntity.setCurrencyByCurrencyId(currencyEntity);
        existingPaymentEntity.setPaymentTypeByPaymentTypeId(paymentTypeEntity);
        existingPaymentEntity.setPaymentAmount(amount);
        existingPaymentEntity.setCreated(new Timestamp(System.currentTimeMillis()));
        existingPaymentEntity.setUniqueIdByUniqueId(newUniqueEntity);

        existingPaymentRepository.save(existingPaymentEntity);

        return existingPaymentEntity;
    }
}
