package com.luminor.task.payment.db.repository;

import com.luminor.task.payment.db.entity.CanceledPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanceledPaymentRepository extends JpaRepository<CanceledPaymentEntity, Integer> {
}
