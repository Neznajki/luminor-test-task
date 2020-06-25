package com.luminor.task.payment.scheduler;

import com.luminor.task.payment.db.entity.ClientIpEntity;
import com.luminor.task.payment.db.repository.ClientIpRepository;
import com.luminor.task.payment.external.api.CountryByIpGather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class GatherCountriesTimerTask {

    Logger logger = LoggerFactory.getLogger(GatherCountriesTimerTask.class);
    ClientIpRepository clientIpRepository;
    CountryByIpGather countryByIpGather;

    public GatherCountriesTimerTask(ClientIpRepository clientIpRepository, CountryByIpGather countryByIpGather) {
        this.clientIpRepository = clientIpRepository;
        this.countryByIpGather = countryByIpGather;
    }

    @Scheduled(fixedRate = 60000)
    public void gatherCountryInfo() {
        Collection<ClientIpEntity> unknownRecordsCollection = clientIpRepository.findUnknownCountries();

        for (ClientIpEntity clientIpEntity: unknownRecordsCollection) {
            if (
                clientIpEntity.getIpAddress().equals("127.0.0.1") ||
                clientIpEntity.getIpAddress().equals("0:0:0:0:0:0:0:1")
            ) {
                clientIpEntity.setCountryCode("LV");//localhost ??
            } else {
                String countryCode = getCountryCode(clientIpEntity);

                if (countryCode == null) {
                    continue;
                }

                clientIpEntity.setCountryCode(countryCode);
            }

            if (clientIpEntity.getCountryCode() != null) {
                clientIpRepository.save(clientIpEntity);
            }
        }
    }

    protected String getCountryCode(ClientIpEntity clientIpEntity) {
        try {
            String countryCode = countryByIpGather.getCountryCode(clientIpEntity.getIpAddress());

            if (countryCode == null) {
                logger.warn(String.format("could not find country for %s", clientIpEntity.getIpAddress()));
                return null;
            }

            logger.info(String.format("found %s country for IP %s", countryCode, clientIpEntity.getIpAddress()));

            return countryCode;
        } catch (Exception e) {
            logger.error(String.format("could not find country for %s", clientIpEntity.getIpAddress()), e);
            return null;
        }
    }
}
