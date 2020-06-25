package com.luminor.task.payment.db.repository;

import com.luminor.task.payment.db.entity.AllowedTypeCurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllowedTypeCurrencyRepository extends JpaRepository<AllowedTypeCurrencyEntity, Integer> {
}
