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

    @Autowired
    public PaymentDataServiceImpl(ExistingPaymentDataRepository existingPaymentDataRepository) {
        this.existingPaymentDataRepository = existingPaymentDataRepository;
    }

    public void validatePaymentType(
        CreatePaymentRequest createPaymentRequest,
        AllowedTypeCurrencyEntity allowedTypeCurrencyEntity
    ) throws InvalidPaymentException {
        if (createPaymentRequest.getCreditorIban().equals("") || createPaymentRequest.getCreditorIban() == null) {
            throw new InvalidPaymentException("creditorIban field is mandatory");
        }

        if (createPaymentRequest.getDebtorIban().equals("") || createPaymentRequest.getDebtorIban() == null) {
            throw new InvalidPaymentException("debtorIban field is mandatory");
        }

        if (
            allowedTypeCurrencyEntity.getPaymentTypeByTypeId().getTypeName().equals("TYPE1") && (
                createPaymentRequest.getDetails().equals("") || createPaymentRequest.getDetails() == null
            )
        ) {
            throw new InvalidPaymentException("details field is mandatory for TYPE1");
        }

        if (
            allowedTypeCurrencyEntity.getPaymentTypeByTypeId().getTypeName().equals("TYPE3") && (
                createPaymentRequest.getCreditorBankBicCode().equals("") || createPaymentRequest.getCreditorBankBicCode() == null
            )
        ) {
            throw new InvalidPaymentException("creditorBankBicCode field is mandatory for TYPE3");
        }
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
