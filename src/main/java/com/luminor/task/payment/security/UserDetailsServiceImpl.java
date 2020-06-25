package com.luminor.task.payment.security;

import com.luminor.task.payment.db.entity.ClientEntity;
import com.luminor.task.payment.db.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ClientRepository clientRepository;

    @Autowired
    public UserDetailsServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        ClientEntity user = clientRepository.findByLogin(username);
        if (user == null) throw new UsernameNotFoundException(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        if (username.equals("RESTapi")) {
            grantedAuthorities.add(new SimpleGrantedAuthority("RESTApi"));
        }

        return new org.springframework.security.core.userdetails.User(
            user.getLogin(),
            user.getEncryptedPass(),
            grantedAuthorities
        );
    }
}
