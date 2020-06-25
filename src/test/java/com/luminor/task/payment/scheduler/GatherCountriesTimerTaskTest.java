package com.luminor.task.payment.scheduler;

import com.luminor.task.payment.db.entity.ClientIpEntity;
import com.luminor.task.payment.db.repository.ClientIpRepository;
import com.luminor.task.payment.external.api.CountryByIpGather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GatherCountriesTimerTaskTest {
    @MockBean
    GatherCountriesTimerTask gatherCountriesTimerTask;
    @MockBean
    ClientIpRepository clientIpRepository;
    @MockBean
    CountryByIpGather countryByIpGather;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(gatherCountriesTimerTask, "clientIpRepository", clientIpRepository);
        ReflectionTestUtils.setField(gatherCountriesTimerTask, "countryByIpGather", countryByIpGather);
    }

    public static Object[][] dataProviderForGatherCountryInfoTest() {
        return new Object[][]{
            {
                new ClientIpRecord[] {
                    new ClientIpRecord("127.0.0.1", "LV", true),
                    new ClientIpRecord("0:0:0:0:0:0:4:1", "unit test", false),
                    new ClientIpRecord("127.0.0.11", null, false),
                }
            },
            {
                new ClientIpRecord[] {
                    new ClientIpRecord("0:0:0:0:0:0:0:1", "LV", true),
                    new ClientIpRecord("127.0.0.11", "unit test", false),
                    new ClientIpRecord("0:0:0:0:0:0:4:1", null, false),
                }
            },
            {new ClientIpRecord[] {}},
        };
    }

    @ParameterizedTest
    @MethodSource(value = "dataProviderForGatherCountryInfoTest")
    public void gatherCountryInfoTest(ClientIpRecord[] unknownRecords) {
        doCallRealMethod().when(gatherCountriesTimerTask).gatherCountryInfo();
        Collection<ClientIpEntity> collection = new ArrayList<>(unknownRecords.length);
        Collection<ClientIpEntity> notSavingEntities = new ArrayList<>();

        for (ClientIpRecord ipRecord : unknownRecords) {
            ClientIpEntity entity = new ClientIpEntity();

            entity.setIpAddress(ipRecord.ipAddress);
            if (ipRecord.isLocal) {
                entity.setCountryCode("LV");
            }

            collection.add(entity);
            if (ipRecord.countryCode != null) {
                entity.setCountryCode(ipRecord.countryCode);
                when(clientIpRepository.save(entity)).thenReturn(entity);
            } else {
                notSavingEntities.add(entity);
            }
        }

        when(clientIpRepository.findUnknownCountries()).thenReturn(collection);
        gatherCountriesTimerTask.gatherCountryInfo();

        for (ClientIpEntity clientIpEntity: notSavingEntities) {
            verify(clientIpRepository, never()).save(clientIpEntity);
        }
    }

    private static class ClientIpRecord {
        public String ipAddress;
        public String countryCode;
        public Boolean isLocal;

        public ClientIpRecord(String ipAddress, String countryCode, Boolean isLocal) {
            this.ipAddress = ipAddress;
            this.countryCode = countryCode;
            this.isLocal = isLocal;
        }
    }

    public static Object[][] dataProviderForGetCountryCodeTest() {
        return new Object[][]{
            {"test ip", "test country", false, "test country"},
            {"test ip", null, false, null},
            {"test ip", "test country", true, null},
        };
    }

    @ParameterizedTest
    @MethodSource(value = "dataProviderForGetCountryCodeTest")
    public void getCountryCodeTest(String ipAddress, String countryCode, Boolean exception, String expectingResult) throws IOException {
        Logger logger = Mockito.mock(Logger.class);
        ReflectionTestUtils.setField(gatherCountriesTimerTask, "logger", logger);

        ClientIpEntity clientIpEntity = new ClientIpEntity();
        clientIpEntity.setIpAddress(ipAddress);

        Exception testExceptionCatch = null;
        when(gatherCountriesTimerTask.getCountryCode(clientIpEntity)).thenCallRealMethod();

        if (exception) {
            testExceptionCatch = new IOException("test exception catch");
            when(countryByIpGather.getCountryCode(ipAddress)).thenThrow(testExceptionCatch);
        } else {
            when(countryByIpGather.getCountryCode(ipAddress)).thenReturn(countryCode);
        }

        String result = gatherCountriesTimerTask.getCountryCode(clientIpEntity);

        assertEquals(expectingResult, result);

        if (exception) {
            verify(logger, times(1)).error(String.format("could not find country for %s", ipAddress), testExceptionCatch);
        } else if (countryCode == null) {
            verify(logger, times(1)).warn(String.format("could not find country for %s", ipAddress));
        } else {
            verify(logger, times(1)).info(String.format("found %s country for IP %s", countryCode, ipAddress));
        }

        //        try {
        //            String countryCode = countryByIpGather.getCountryCode(clientIpEntity.getIpAddress());
        //
        //            if (countryCode == null) {
        //                logger.warn(String.format("could not find country for %s", clientIpEntity.getIpAddress()));
        //                return null;
        //            }
        //
        //            logger.info(String.format("found %s country for IP %s", countryCode, clientIpEntity.getIpAddress()));
        //
        //            return countryCode;
        //        } catch (Exception e) {
        //            logger.error(String.format("could not find country for %s", clientIpEntity.getIpAddress()), e);
        //            return null;
        //        }
    }
}
