package com.luminor.task.payment.security;

import com.luminor.task.payment.db.entity.ClientEntity;
import com.luminor.task.payment.db.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;


import static org.junit.Assert.*;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;


@SpringBootTest
public class UserDetailsServiceImplTest {
    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(userDetailsService, "clientRepository", clientRepository);
    }

    @Test
    public void loadUserByUsernameNoUserTest() {
        //        ClientEntity user = clientRepository.findByLogin(username);
        //        if (user == null) throw new UsernameNotFoundException(username);

        String loginMock = "test login";
        when(clientRepository.findByLogin(loginMock)).thenReturn(null);

        doCallRealMethod().when(userDetailsService).loadUserByUsername(loginMock);

        UsernameNotFoundException e = assertThrows(
            UsernameNotFoundException.class,
            () -> userDetailsService.loadUserByUsername(loginMock)
        );

        assertEquals(loginMock, e.getMessage());
    }

    @Test
    public void loadUserByUsernameTest() {
        String loginMock = "test login";
        String passwordMock = "test password";
        ClientEntity clientEntity = new ClientEntity();

        clientEntity.setLogin(loginMock);
        clientEntity.setEncryptedPass(passwordMock);

        when(clientRepository.findByLogin(loginMock)).thenReturn(clientEntity);

        doCallRealMethod().when(userDetailsService).loadUserByUsername(loginMock);

        UserDetails userDetail = userDetailsService.loadUserByUsername(loginMock);

        //could be refactor to assertThat ??
        assertEquals(loginMock, userDetail.getUsername());
        assertEquals(passwordMock, userDetail.getPassword());
        assertTrue(userDetail.getAuthorities().isEmpty());
    }
}
