package com.luminor.task.payment.payment;

import com.luminor.task.payment.db.entity.AllowedTypeCurrencyEntity;
import com.luminor.task.payment.db.entity.ExistingPaymentDataEntity;
import com.luminor.task.payment.db.entity.ExistingPaymentEntity;
import com.luminor.task.payment.db.repository.ExistingPaymentDataRepository;
import com.luminor.task.payment.web.rest.request.CreatePaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentDataServiceImpl {
    ExistingPaymentDataRepository existingPaymentDataRepository;
    private final ValidatorCollection validatorCollection;

    @Autowired
    public PaymentDataServiceImpl(
        ExistingPaymentDataRepository existingPaymentDataRepository,
        ValidatorCollection validatorCollection
    ) {
        this.existingPaymentDataRepository = existingPaymentDataRepository;
        this.validatorCollection = validatorCollection;
    }

    public void validatePaymentType(
        CreatePaymentRequest createPaymentRequest,
        AllowedTypeCurrencyEntity allowedTypeCurrencyEntity
    ) throws InvalidPaymentException {
        validatorCollection.validate(allowedTypeCurrencyEntity.getPaymentTypeByTypeId().getTypeName(), createPaymentRequest);
    }

    public ExistingPaymentDataEntity createEntity(CreatePaymentRequest createPaymentRequest, ExistingPaymentEntity paymentEntity) {
        ExistingPaymentDataEntity existingPaymentDataEntity = new ExistingPaymentDataEntity();

        existingPaymentDataEntity.setExistingPaymentByPaymentId(paymentEntity);
        existingPaymentDataEntity.setDetails(createPaymentRequest.getDetails());
        existingPaymentDataEntity.setCreditorBankBicCode(createPaymentRequest.getCreditorBankBicCode());
        existingPaymentDataEntity.setCreditorIban(createPaymentRequest.getCreditorIban());
        existingPaymentDataEntity.setDebtorIban(createPaymentRequest.getDebtorIban());

        existingPaymentDataRepository.save(existingPaymentDataEntity);

        return existingPaymentDataEntity;
    }
}
