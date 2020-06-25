package com.luminor.task.payment.db.repository;

import com.luminor.task.payment.db.entity.ExternalRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalRequestRepository extends JpaRepository<ExternalRequestEntity, Integer> {
}
