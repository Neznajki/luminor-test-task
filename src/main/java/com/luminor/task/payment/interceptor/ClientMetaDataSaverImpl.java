package com.luminor.task.payment.interceptor;

import com.luminor.task.payment.db.entity.ClientActionEntity;
import com.luminor.task.payment.db.entity.ClientEntity;
import com.luminor.task.payment.db.entity.ClientIpEntity;
import com.luminor.task.payment.db.repository.ClientActionRepository;
import com.luminor.task.payment.db.repository.ClientIpRepository;
import com.luminor.task.payment.db.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@Service
public class ClientMetaDataSaverImpl {
    private final String[] IP_HEADER_CANDIDATES = {
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
    };

    private final ClientIpRepository clientIpRepository;
    private final ClientRepository clientRepository;
    private final ClientActionRepository clientActionRepository;

    @Autowired
    public ClientMetaDataSaverImpl(
        ClientIpRepository clientIpRepository,
        ClientRepository clientRepository,
        ClientActionRepository clientActionRepository
    ) {
        this.clientIpRepository = clientIpRepository;
        this.clientRepository = clientRepository;
        this.clientActionRepository = clientActionRepository;
    }

    public UserData createRequestUserMetaData(HttpServletRequest request) {
        UserData userData = new UserData();
        String clientIp = getClientIp(request);
        ClientIpEntity clientIpEntity = clientIpRepository.findByIpAddress(clientIp);

        if (clientIpEntity == null) {
            clientIpEntity = createClientIpEntity(clientIp);
        }

        userData.setClientIpEntity(clientIpEntity);

        if (request.getRemoteUser() != null) {
            userData.setClientActionEntity(createClientAction(request, clientIpEntity));
        }


        return userData;
    }

    private ClientActionEntity createClientAction(HttpServletRequest request, ClientIpEntity clientIpEntity) {
        ClientEntity clientEntity = clientRepository.findByLogin(request.getRemoteUser());
        if (clientEntity == null) {
            throw new UsernameNotFoundException(request.getRemoteUser());//could happen if you delete logged in user not real use-case
        }
        ClientActionEntity clientActionEntity = new ClientActionEntity();

        clientActionEntity.setClientByClientId(clientEntity);
        clientActionEntity.setClientIpByClientIpId(clientIpEntity);
        clientActionEntity.setTimestamp(new Timestamp(System.currentTimeMillis()));

        clientActionRepository.save(clientActionEntity);
        return clientActionEntity;
    }

    private ClientIpEntity createClientIpEntity(String clientIp) {
        ClientIpEntity clientIpEntity;
        clientIpEntity = new ClientIpEntity();
        clientIpEntity.setIpAddress(clientIp);

        try {
            clientIpRepository.save(clientIpEntity);
        } catch (DataIntegrityViolationException e) {
            clientIpEntity = clientIpRepository.findByIpAddress(clientIp);
        }

        return clientIpEntity;
    }

    private String getClientIp(HttpServletRequest request) {
        for (String header: IP_HEADER_CANDIDATES) {
            String ipList = request.getHeader(header);
            if (ipList != null && ipList.length() != 0 && !"unknown".equalsIgnoreCase(ipList)) {
                return ipList.split(",")[0];
            }
        }

        return request.getRemoteAddr();
    }
}
