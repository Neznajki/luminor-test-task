package com.luminor.task.payment.db.repository;

import com.luminor.task.payment.db.entity.ExistingPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ExistingPaymentRepository extends JpaRepository<ExistingPaymentEntity, Integer> {
    @Query(value = "SELECT * FROM existing_payment JOIN client c on existing_payment.payed_client_id = c.id WHERE login = :userName", nativeQuery=true)
    Collection<ExistingPaymentEntity> getUserExistingPayments(String userName);

    ExistingPaymentEntity getByUniqueIdByUniqueIdHashValue(String UUID);

    Collection<ExistingPaymentEntity> getAllByCanceledPaymentEntityIsNotNull();
    Collection<ExistingPaymentEntity> getAllByCanceledPaymentEntityIsNull();

    @Query(value = "SELECT * FROM existing_payment JOIN canceled_payment cp on existing_payment.id = cp.existing_payment_id WHERE existing_payment.payment_amount BETWEEN :min AND :max", nativeQuery = true)
    Collection<ExistingPaymentEntity> findAllCanceledByMinMaxAmount(Double min, Double max);

    @Query(value = "SELECT * FROM existing_payment JOIN canceled_payment cp on existing_payment.id = cp.existing_payment_id WHERE existing_payment.payment_amount > :min", nativeQuery = true)
    Collection<ExistingPaymentEntity> findAllCanceledByMinAmount(Double min);

    @Query(value = "SELECT * FROM existing_payment JOIN canceled_payment cp on existing_payment.id = cp.existing_payment_id WHERE existing_payment.payment_amount < :max", nativeQuery = true)
    Collection<ExistingPaymentEntity> findAllCanceledByMaxAmount(Double max);
}
