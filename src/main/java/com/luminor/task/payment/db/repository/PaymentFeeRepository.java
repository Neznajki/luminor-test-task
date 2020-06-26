package com.luminor.task.payment.db.repository;

import com.luminor.task.payment.db.entity.PaymentFeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentFeeRepository extends JpaRepository<PaymentFeeEntity, Integer> {
}
