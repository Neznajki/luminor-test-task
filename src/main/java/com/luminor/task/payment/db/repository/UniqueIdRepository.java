package com.luminor.task.payment.db.repository;

import com.luminor.task.payment.db.entity.UniqueIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniqueIdRepository extends JpaRepository<UniqueIdEntity, Integer> {
    UniqueIdEntity findByHashValue(String hashValue);
}
