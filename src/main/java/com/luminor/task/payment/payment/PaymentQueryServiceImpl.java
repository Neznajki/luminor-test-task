package com.luminor.task.payment.payment;

import com.luminor.task.payment.db.entity.ExistingPaymentEntity;
import com.luminor.task.payment.db.repository.ExistingPaymentRepository;
import com.luminor.task.payment.web.rest.request.AmountFilterRequest;
import com.luminor.task.payment.web.rest.response.ExistingPaymentDataResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PaymentQueryServiceImpl {
    ExistingPaymentRepository existingPaymentRepository;

    @Autowired
    public PaymentQueryServiceImpl(ExistingPaymentRepository existingPaymentRepository) {
        this.existingPaymentRepository = existingPaymentRepository;
    }

    public ExistingPaymentDataResponse getByUUID(String UUID) throws InvalidPaymentException {
        if (UUID == null || UUID.equals("")) {
            throw new InvalidPaymentException("payment UUID is required message format {\"UUID\":\"dcc711cd-63c7-4fc9-9658-a1efa1bb9523\"}");
        }

        LoggerFactory.getLogger(PaymentQueryServiceImpl.class).info(String.format("searching for %s", UUID));
        ExistingPaymentEntity entity = existingPaymentRepository.getByUniqueIdByUniqueIdHashValue(UUID);

        if (entity == null) {
            throw new InvalidPaymentException(String.format("could not find payment with id %s", UUID));
        }

        return ExistingPaymentDataResponse.fromExistingEntity(
            entity
        );
    }

    public Collection<ExistingPaymentDataResponse> getNotCanceledItems()
    {
        return ExistingPaymentDataResponse.fromEntityCollection(existingPaymentRepository.getAllByCanceledPaymentEntityIsNull());
    }

    public Collection<ExistingPaymentDataResponse> getCanceledItems(AmountFilterRequest amountFilterRequest)
    {
        if (amountFilterRequest.getMin() != null && amountFilterRequest.getMax() != null) {
            return ExistingPaymentDataResponse.fromEntityCollection(
                existingPaymentRepository.findAllCanceledByMinMaxAmount(
                    amountFilterRequest.getMin(),
                    amountFilterRequest.getMax()
                )
            );
        }

        if (amountFilterRequest.getMin() != null) {
            return ExistingPaymentDataResponse.fromEntityCollection(
                existingPaymentRepository.findAllCanceledByMinAmount(
                    amountFilterRequest.getMin()
                )
            );
        }

        if (amountFilterRequest.getMax() != null) {
            return ExistingPaymentDataResponse.fromEntityCollection(
                existingPaymentRepository.findAllCanceledByMaxAmount(
                    amountFilterRequest.getMax()
                )
            );
        }

        return ExistingPaymentDataResponse.fromEntityCollection(existingPaymentRepository.getAllByCanceledPaymentEntityIsNotNull());
    }
}
