package com.luminor.task.payment.interceptor;

import com.luminor.task.payment.db.entity.ClientActionEntity;
import com.luminor.task.payment.db.entity.ClientEntity;
import com.luminor.task.payment.db.entity.ClientIpEntity;
import com.luminor.task.payment.db.repository.ClientActionRepository;
import com.luminor.task.payment.db.repository.ClientIpRepository;
import com.luminor.task.payment.db.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.http.HttpServletRequest;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ClientMetaDataSaverImplTest {
    @MockBean
    ClientMetaDataSaverImpl clientMetaDataSaver;

    @MockBean
    ClientIpRepository clientIpRepository;
    @MockBean
    ClientRepository clientRepository;
    @MockBean
    ClientActionRepository clientActionRepository;

    @BeforeEach
    public void setup() {
        //could be simplified by something like foreach (private field: get all fields) {setField, getParamName, getParamRequiredCLass}
        ReflectionTestUtils.setField(clientMetaDataSaver, "clientIpRepository", clientIpRepository);
        ReflectionTestUtils.setField(clientMetaDataSaver, "clientRepository", clientRepository);
        ReflectionTestUtils.setField(clientMetaDataSaver, "clientActionRepository", clientActionRepository);
    }


    @Test
    public void headerConstTest() {
        assertArrayEquals(new String[]{
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
        }, (String[])ReflectionTestUtils.getField(new ClientMetaDataSaverImpl(
            Mockito.mock(ClientIpRepository.class),
            Mockito.mock(ClientRepository.class),
            Mockito.mock(ClientActionRepository.class)
        ), "IP_HEADER_CANDIDATES"));
    }

    public static Object[][] dataProviderForCreateRequestUserMetaDataTest() {
        return new Object[][]{
            {/* existingIpEntity */null,/* remoteUser */"LL"},
            {/* existingIpEntity */Mockito.mock(ClientIpEntity.class),/* remoteUser */"LL"},
            {/* existingIpEntity */null,/* remoteUser */null},
            {/* existingIpEntity */Mockito.mock(ClientIpEntity.class),/* remoteUser */null},
        };
    }


    @ParameterizedTest
    @MethodSource(value = "dataProviderForCreateRequestUserMetaDataTest")
    public void createRequestUserMetaDataTest(ClientIpEntity existingIpEntity, String remoteUser) {
        String clientIpMock = "test client ip";
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        when(request.getRemoteUser()).thenReturn(remoteUser);
        when(clientMetaDataSaver.createRequestUserMetaData(request)).thenCallRealMethod();
        when(clientMetaDataSaver.getClientIp(request)).thenReturn(clientIpMock);
        when(clientIpRepository.findByIpAddress(clientIpMock)).thenReturn(existingIpEntity);

        ClientIpEntity actualEntity = existingIpEntity;
        if (existingIpEntity == null) {
            actualEntity = Mockito.mock(ClientIpEntity.class);
            when(clientMetaDataSaver.createClientIpEntity(clientIpMock)).thenReturn(actualEntity);
        }

        ClientActionEntity clientActionEntity = null;

        if (remoteUser != null) {
            clientActionEntity = Mockito.mock(ClientActionEntity.class);
            when(clientMetaDataSaver.createClientAction(request, actualEntity)).thenReturn(clientActionEntity);
        }

        UserData userData = new UserData();

        userData.setClientIpEntity(actualEntity);
        userData.setClientActionEntity(clientActionEntity);

        UserData response = clientMetaDataSaver.createRequestUserMetaData(request);
        assertEquals(userData, response);

        if (existingIpEntity != null) {
            verify(clientMetaDataSaver, never()).createClientIpEntity(any());
        }
        if (remoteUser == null) {
            verify(clientMetaDataSaver, never()).createClientAction(any(), any());
        }
    }

    @Test
    public void createClientActionExceptionTest() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        ClientIpEntity clientIpEntity = Mockito.mock(ClientIpEntity.class);
        when(clientMetaDataSaver.createClientAction(request, clientIpEntity)).thenCallRealMethod();
        String userMock = "unite test user";

        when(request.getRemoteUser()).thenReturn(userMock);
        when(clientRepository.findByLogin(userMock)).thenReturn(null);

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> clientMetaDataSaver.createClientAction(request, clientIpEntity));

        assertEquals(userMock, exception.getMessage());
    }

    @Test
    public void createClientActionTest() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        ClientIpEntity clientIpEntity = Mockito.mock(ClientIpEntity.class);
        ClientEntity clientEntity = Mockito.mock(ClientEntity.class);

        when(clientMetaDataSaver.createClientAction(request, clientIpEntity)).thenCallRealMethod();
        String userMock = "unite test user";

        when(request.getRemoteUser()).thenReturn(userMock);
        when(clientRepository.findByLogin(userMock)).thenReturn(clientEntity);

        Timestamp before = new Timestamp(System.currentTimeMillis());
        ClientActionEntity result = clientMetaDataSaver.createClientAction(request, clientIpEntity);
        Timestamp after = new Timestamp(System.currentTimeMillis());

        assertEquals(clientEntity, result.getClientByClientId());
        assertEquals(clientIpEntity, result.getClientIpByClientIpId());

        //timestamp is realtime time
        assertTrue(result.getTimestamp().equals(before) || result.getTimestamp().after(before));
        assertTrue(result.getTimestamp().equals(after) || result.getTimestamp().before(after));
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void createClientIpEntityTest(Boolean duplicateKeyEntry) {
        String ipMock = "client test ip";

        when(clientMetaDataSaver.createClientIpEntity(ipMock)).thenCallRealMethod();

        ClientIpEntity clientIpEntity = new ClientIpEntity();
        clientIpEntity.setIpAddress(ipMock);

        ClientIpEntity expectingResponse = clientIpEntity;
        if (duplicateKeyEntry) {
            when(clientIpRepository.save(clientIpEntity)).thenThrow(new DataIntegrityViolationException("test"));
            expectingResponse = Mockito.mock(ClientIpEntity.class);
            when(clientIpRepository.findByIpAddress(ipMock)).thenReturn(expectingResponse);
        } else {
            when(clientIpRepository.save(clientIpEntity)).thenReturn(clientIpEntity);
        }

        assertEquals(expectingResponse, clientMetaDataSaver.createClientIpEntity(ipMock));
        if (! duplicateKeyEntry) {
            verify(clientIpRepository, never()).findByIpAddress(any());
        }
    }


    public static Object[][] dataProviderForGetClientIpTest() {
        return new Object[][]{
            {/* IP_HEADER_CANDIDATES */new String[]{"a","d","b","g"},/* remoteAddr */"LL",/* expectedResultCandidate */ "b"},
            {/* IP_HEADER_CANDIDATES */new String[]{"a","b"},/* remoteAddr */"LL",/* expectedResultCandidate */ "a"},
            {/* IP_HEADER_CANDIDATES */new String[]{"a","b"},/* remoteAddr */"LL",/* expectedResultCandidate */ null},
        };
    }

    @ParameterizedTest
    @MethodSource(value = "dataProviderForGetClientIpTest")
    public void getClientIpTest(String[] IP_HEADER_CANDIDATES, String remoteAddr, String expectedResultCandidate) {
        String expectingResponse = "TT";
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        if (expectedResultCandidate == null) {
            expectingResponse = remoteAddr;
        }

        ReflectionTestUtils.setField(clientMetaDataSaver, "IP_HEADER_CANDIDATES", IP_HEADER_CANDIDATES);

        when(clientMetaDataSaver.getClientIp(request)).thenCallRealMethod();

        for (String header: IP_HEADER_CANDIDATES) {
            when(clientMetaDataSaver.getHeaderIp(request, header)).thenReturn(header.equals(expectedResultCandidate) ? expectingResponse: null);
        }

        if (expectedResultCandidate == null) {
            when(request.getRemoteAddr()).thenReturn(remoteAddr);
        }

        String response = clientMetaDataSaver.getClientIp(request);
        assertEquals(expectingResponse, response);

        if (expectedResultCandidate != null) {
            verify(request, never()).getRemoteAddr();
        }
    }

    public static Object[][] dataProviderForGetHeaderIpTest() {
        return new Object[][]{
            {/* name */"aa",/* value */"unknown",/* response */ null},
            {/* name */"aa",/* value */"",/* response */ null},
            {/* name */"aa",/* value */null,/* response */ null},
            {/* name */"aa",/* value */"127.0.0.1",/* response */ "127.0.0.1"},
            {/* name */"aa",/* value */"127.0.0.1,should not be handled",/* response */ "127.0.0.1"},
        };
    }

    @ParameterizedTest
    @MethodSource(value = "dataProviderForGetHeaderIpTest")
    public void getHeaderIpTest(String name, String value, String response) {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(clientMetaDataSaver.getHeaderIp(request, name)).thenCallRealMethod();
        when(request.getHeader(name)).thenReturn(value);

        assertEquals(response, clientMetaDataSaver.getHeaderIp(request, name));
    }
}
