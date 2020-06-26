package com.luminor.task.payment.db.repository;

import com.luminor.task.payment.db.entity.ExistingPaymentDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExistingPaymentDataRepository extends JpaRepository<ExistingPaymentDataEntity, Integer> {
}
