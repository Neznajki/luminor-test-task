package com.luminor.task.payment.db.repository;

import com.luminor.task.payment.db.entity.ClientIpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ClientIpRepository extends JpaRepository<ClientIpEntity, Integer> {
    ClientIpEntity findByIpAddress(String ipAddress);

    @Query("SELECT ci FROM ClientIpEntity ci WHERE ci.countryCode IS NULL")
    Collection<ClientIpEntity> findUnknownCountries();

}
