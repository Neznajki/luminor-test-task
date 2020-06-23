package com.luminor.task.payment.security;

import com.luminor.task.payment.db.entity.ClientEntity;
import com.luminor.task.payment.db.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceImplTest {
    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        userService = Mockito.mock(UserServiceImpl.class);
        ReflectionTestUtils.setField(userService, "clientRepository", clientRepository);
        ReflectionTestUtils.setField(userService, "bCryptPasswordEncoder", bCryptPasswordEncoder);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void saveTest(Boolean isPasswordChange) {
        ClientEntity clientEntity = Mockito.mock(ClientEntity.class);

        User user = Mockito.mock(User.class);

        if (isPasswordChange) {
            when(user.getClientEntity()).thenReturn(clientEntity);
        } else {
            when(userService.createClientEntity()).thenReturn(clientEntity);
        }

        String passwordMock = "test password", encodedPass = "test encoded pass";
        when(user.getPassword()).thenReturn(passwordMock);
        when(user.getPasswordConfirm()).thenReturn("test password confirm");
        when(user.getLogin()).thenReturn("test login");
        when(user.getPasswordReset()).thenReturn(isPasswordChange);

        doCallRealMethod().when(userService).save(user);
        when(bCryptPasswordEncoder.encode(passwordMock)).thenReturn(encodedPass);
        when(clientRepository.save(clientEntity)).thenReturn(null);

        userService.save(user);

        InOrder orderVerifier = inOrder(clientEntity, clientRepository);

        if (isPasswordChange) {
            orderVerifier.verify(clientEntity, never()).setLogin(any());
        } else {
            //noinspection ResultOfMethodCallIgnored
            verify(user, never()).getClientEntity();
            orderVerifier.verify(clientEntity, times(1)).setLogin(user.getLogin());
        }

        orderVerifier.verify(clientEntity, times(1)).setEncryptedPass(encodedPass);
        orderVerifier.verify(clientRepository, times(1)).save(clientEntity);
    }

    @Test
    public void findByUsernameTest() {
        String userNameMock = "test user name";
        ClientEntity clientEntity = new ClientEntity();

        when(userService.findByUsername(userNameMock)).thenCallRealMethod();
        when(clientRepository.findByLogin(userNameMock)).thenReturn(clientEntity);

        assertEquals(clientEntity, userService.findByUsername(userNameMock));
    }

    @Test
    public void createClientEntityTest() {
        when(userService.createClientEntity()).thenCallRealMethod();

        ClientEntity clientEntity = userService.createClientEntity();
        assertEquals(0, clientEntity.getId());
        assertNull(clientEntity.getLogin());
        assertNull(clientEntity.getEncryptedPass());
    }
}
