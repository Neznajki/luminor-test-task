package com.luminor.task.payment.db.repository;

import com.luminor.task.payment.db.entity.ClientActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientActionRepository extends JpaRepository<ClientActionEntity, Integer> {
}
