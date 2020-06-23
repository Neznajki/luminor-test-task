package com.luminor.task.payment.event;

import com.luminor.task.payment.db.repository.ClientIpRepository;
import com.luminor.task.payment.external.api.CountryByIpGather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Timer;

@Component
public class ContextEventHandler {
//    Logger logger = LoggerFactory.getLogger(ContextEventHandler.class);
//    ClientIpRepository clientIpRepository;
//    Timer gatherCountriesTimer = new Timer();
//    private GatherCountriesTimerTask task;
//    CountryByIpGather countryByIpGather;
//
//    @Autowired
//    public ContextEventHandler(ClientIpRepository clientIpRepository, CountryByIpGather countryByIpGather) {
//        this.clientIpRepository = clientIpRepository;
//        this.countryByIpGather = countryByIpGather;
//    }
//
//    @EventListener
//    public void appStartHandle(ContextStartedEvent contextStartedEvent) {
//        logger.error("context loaded");
//        task = new GatherCountriesTimerTask(logger, clientIpRepository, gatherCountriesTimer, countryByIpGather);
//        gatherCountriesTimer.schedule(task, 1);//launch on boot
//    }
//
//    @EventListener
//    public void appShutdownEvent(ContextStoppedEvent contextStoppedEvent) {
//        task.shutdown = true;
//        gatherCountriesTimer.cancel();
//    }

}
