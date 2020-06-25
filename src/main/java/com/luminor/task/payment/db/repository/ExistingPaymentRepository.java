package com.luminor.task.payment.db.repository;

import com.luminor.task.payment.db.entity.ExistingPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExistingPaymentRepository extends JpaRepository<ExistingPaymentEntity, Integer> {
}
