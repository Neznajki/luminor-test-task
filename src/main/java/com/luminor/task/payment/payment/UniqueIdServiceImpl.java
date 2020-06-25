package com.luminor.task.payment.payment;

import com.luminor.task.payment.db.entity.UniqueIdEntity;
import com.luminor.task.payment.db.repository.UniqueIdRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.UUID;

@Service
public class UniqueIdServiceImpl {
    Logger logger = LoggerFactory.getLogger(UniqueIdServiceImpl.class);

    UniqueIdRepository uniqueIdRepository;
//    EntityManager entityManager;

    @Autowired
    public UniqueIdServiceImpl(
        UniqueIdRepository uniqueIdRepository
    ) {
        this.uniqueIdRepository = uniqueIdRepository;
    }

    public UniqueIdEntity getNewUniqueEntity()
    {
        return tryCreate(0);
    }

    @Transactional
    protected UniqueIdEntity tryCreate(int i)
    {
        try {
            UniqueIdEntity uniqueIdEntity = new UniqueIdEntity();

            uniqueIdEntity.setHashValue(UUID.randomUUID().toString());
            uniqueIdEntity.setGenerationTime(new Timestamp(System.currentTimeMillis()));

            uniqueIdRepository.save(uniqueIdEntity);

//            logger.info(String.format("created %s unique record, with id %d", uniqueIdEntity.getHashValue(), uniqueIdEntity.getId()));

            return uniqueIdEntity;
        } catch (ConstraintViolationException e) {
            if (i < 5) {
                tryCreate(++ i);
            }
            throw new RuntimeException("could not generate new ID for payment");
        }
    }
}
