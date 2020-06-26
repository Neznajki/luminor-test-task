package com.luminor.task.payment.payment;

import com.luminor.task.payment.db.entity.*;
import com.luminor.task.payment.db.repository.*;
import com.luminor.task.payment.event.PaymentCanceledEvent;
import com.luminor.task.payment.event.PaymentCreatedEvent;
import com.luminor.task.payment.helper.Request;
import com.luminor.task.payment.helper.Time;
import com.luminor.task.payment.web.rest.request.CancelPaymentRequest;
import com.luminor.task.payment.web.rest.request.CreatePaymentRequest;
import com.luminor.task.payment.web.rest.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collection;
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
    CancelFeeServiceImpl cancelFeeService;
    CanceledPaymentRepository canceledPaymentRepository;
    PaymentFeeRepository paymentFeeRepository;

    @Autowired
    public PaymentServiceImpl(
        ClientRepository clientRepository,
        AllowedTypeCurrencyRepository allowedTypeCurrencyRepository,
        ExistingPaymentRepository existingPaymentRepository,
        PaymentDataServiceImpl paymentDataService,
        UniqueIdServiceImpl uniqueIdService,
        CurrencyStampServiceImpl currencyStampService,
        ApplicationEventPublisher publisher,
        CancelFeeServiceImpl cancelFeeService,
        CanceledPaymentRepository canceledPaymentRepository,
        PaymentFeeRepository paymentFeeRepository
    ) {
        this.clientRepository = clientRepository;
        this.allowedTypeCurrencyRepository = allowedTypeCurrencyRepository;
        this.existingPaymentRepository = existingPaymentRepository;
        this.paymentDataService = paymentDataService;
        this.uniqueIdService = uniqueIdService;
        this.currencyStampService = currencyStampService;
        this.publisher = publisher;
        this.cancelFeeService = cancelFeeService;
        this.canceledPaymentRepository = canceledPaymentRepository;
        this.paymentFeeRepository = paymentFeeRepository;
    }

    public Collection<ExistingPaymentEntity> getExistingPayments(String userName) {
        return existingPaymentRepository.getUserExistingPayments(userName);
    }

    public CancelPaymentResponse cancelPayment(CancelPaymentRequest cancelPaymentRequest)
    {
        ExistingPaymentEntity existingPaymentEntity =
            existingPaymentRepository.getByUniqueIdByUniqueIdHashValue(cancelPaymentRequest.UUID);

        if (existingPaymentEntity == null) {
            return new CancelPaymentResponse(
                "fail",
                String.format("payment with UUID %s not found", cancelPaymentRequest.UUID)
            );
        }

        if (! cancelFeeService.canCancelPayment(existingPaymentEntity)) {
            return new CancelPaymentResponse(
                "fail",
                String.format("payment with UUID %s can not be canceled", cancelPaymentRequest.UUID)
            );
        }

        CanceledPaymentEntity canceledPaymentEntity = cancelPayment(existingPaymentEntity);

        publisher.publishEvent(new PaymentCanceledEvent(Request.getCurrentUserAction(), canceledPaymentEntity));

        return new CancelPaymentResponse(
            "success",
            cancelPaymentRequest.UUID,
            canceledPaymentEntity.getPaymentFeeById().getFeeAmount(),
            Time.diffTimeHours(
                canceledPaymentEntity.getCanceledTimestamp(),
                canceledPaymentEntity.getExistingPaymentByExistingPaymentId().getCreated()
            ),
            canceledPaymentEntity.getExistingPaymentByExistingPaymentId().getCreated(),
            canceledPaymentEntity.getCanceledTimestamp()
        );
    }

    public CreatePaymentResponse createPayment(CreatePaymentRequest paymentRequest)
    {
        ClientEntity clientEntity = clientRepository.findByLogin(paymentRequest.getClientName());
        if (clientEntity == null) {
            return new CreatePaymentResponse("fail", String.format("%s user not found", paymentRequest.getClientName()));
        }
        Optional<AllowedTypeCurrencyEntity> allowedTypeCurrencyEntityOptional = allowedTypeCurrencyRepository.findById(paymentRequest.getAllowedTypeCurrencyId());
        if (allowedTypeCurrencyEntityOptional.isEmpty()) {
            return new CreatePaymentResponse("fail", String.format("%d allowed type currency not found", paymentRequest.getAllowedTypeCurrencyId()));
        }
        AllowedTypeCurrencyEntity allowedTypeCurrencyEntity = allowedTypeCurrencyEntityOptional.get();

        try {
            paymentDataService.validatePaymentType(paymentRequest, allowedTypeCurrencyEntity);
        } catch (InvalidPaymentException exception) {
            return new CreatePaymentResponse("fail", exception.getMessage());
        }

        ClientActionEntity currentUserAction = Request.getCurrentUserAction();

        ExistingPaymentEntity existingPaymentEntity = this.createExistingPaymentEntity(
            paymentRequest,
            clientEntity,
            allowedTypeCurrencyEntity.getCurrencyByCurrencyId(),
            allowedTypeCurrencyEntity.getPaymentTypeByTypeId(),
            paymentRequest.getAmount(),
            currentUserAction
        );

        publisher.publishEvent(new PaymentCreatedEvent(existingPaymentEntity, currentUserAction));

        return new CreatePaymentResponse("success", 1, existingPaymentEntity.getUniqueIdByUniqueId().getHashValue());
    }

    @Transactional
    public ExistingPaymentEntity createExistingPaymentEntity(
        CreatePaymentRequest paymentRequest,
        ClientEntity clientEntity,
        CurrencyEntity currencyEntity,
        PaymentTypeEntity paymentTypeEntity,
        Double amount,
        ClientActionEntity currentUserAction
    ) {
        UniqueIdEntity newUniqueEntity = uniqueIdService.getNewUniqueEntity();
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

        paymentDataService.createEntity(paymentRequest, existingPaymentEntity);

        return existingPaymentEntity;
    }

    @Transactional
    protected CanceledPaymentEntity cancelPayment(ExistingPaymentEntity existingPaymentEntity) {
        Double fee = cancelFeeService.getCalculatedFee(existingPaymentEntity);

        CanceledPaymentEntity canceledPaymentEntity = new CanceledPaymentEntity();

        canceledPaymentEntity.setExistingPaymentByExistingPaymentId(existingPaymentEntity);
        canceledPaymentEntity.setCanceledTimestamp(new Timestamp(System.currentTimeMillis()));

        canceledPaymentRepository.save(canceledPaymentEntity);

        PaymentFeeEntity paymentFeeEntity = new PaymentFeeEntity();

        paymentFeeEntity.setCalculatedAt(new Timestamp(System.currentTimeMillis()));
        paymentFeeEntity.setCanceledPaymentByCancelId(canceledPaymentEntity);
        paymentFeeEntity.setCurrencyByCurrencyId(existingPaymentEntity.getCurrencyByCurrencyId());
        paymentFeeEntity.setFeeAmount(fee);
        paymentFeeEntity.setFeeCoefficient(existingPaymentEntity.getPaymentTypeByPaymentTypeId().getFeeCoefficient());
        paymentFeeEntity.setPaymentTypeByTypeId(existingPaymentEntity.getPaymentTypeByPaymentTypeId());

        try {
            paymentFeeRepository.save(paymentFeeEntity);
            canceledPaymentEntity.setPaymentFeeById(paymentFeeEntity);
        } catch (Exception e) {
            canceledPaymentRepository.delete(canceledPaymentEntity);
            paymentFeeRepository.delete(paymentFeeEntity);
            throw e;
        }
        return canceledPaymentEntity;
    }
}
