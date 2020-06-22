package com.luminor.task.payment.security;

import com.luminor.task.payment.contract.UserService;
import com.luminor.task.payment.db.entity.ClientEntity;
import com.luminor.task.payment.db.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final ClientRepository clientRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(ClientRepository clientRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.clientRepository = clientRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void save(User user) {
        ClientEntity clientEntity;

        if (user.getPasswordReset()) {
            clientEntity = user.getClientEntity();
        } else {
            clientEntity = new ClientEntity();
            clientEntity.setLogin(user.getLogin());
        }

        clientEntity.setEncryptedPass(bCryptPasswordEncoder.encode(user.getPassword()));
        clientRepository.save(clientEntity);
    }

    @Override
    public ClientEntity findByUsername(String username) {
        return clientRepository.findByLogin(username);
    }
}
