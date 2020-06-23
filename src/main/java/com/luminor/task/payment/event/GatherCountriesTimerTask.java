package com.luminor.task.payment.event;

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
                try {
                    String countryCode = countryByIpGather.getCountryCode(clientIpEntity.getIpAddress());

                    if (countryCode == null) {
                        logger.warn(String.format("could not find country for %s", clientIpEntity.getIpAddress()));
                        continue;
                    }

                    logger.info(String.format("found %s country for IP %s", countryCode, clientIpEntity.getIpAddress()));
                    clientIpEntity.setCountryCode(countryCode);
                } catch (Exception e) {
                    logger.error(String.format("could not find country for %s", clientIpEntity.getIpAddress()), e);
                    continue;
                }
            }


            if (clientIpEntity.getCountryCode() != null) {
                clientIpRepository.save(clientIpEntity);
            }
        }
    }
}
