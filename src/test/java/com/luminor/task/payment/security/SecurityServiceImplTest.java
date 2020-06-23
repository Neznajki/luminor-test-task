package com.luminor.task.payment.security;

import com.luminor.task.payment.db.entity.ClientEntity;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SecurityServiceImplTest {
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private SecurityServiceImpl securityService;
    @MockBean
    private Logger logger;

    @Test
    public void findLoggedInUsernameNotLoggedTest() {
        doCallRealMethod().when(securityService).findLoggedInUsername();
        assertNull(securityService.findLoggedInUsername());
    }

    @Test
    public void findLoggedInUsernameLoggedTest() {
        String userName = "test";
        login(userName, "test");
        doCallRealMethod().when(securityService).findLoggedInUsername();
        assertEquals(userName, securityService.findLoggedInUsername());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void autoLoginTest(Boolean success) {
        String loginMock = "test login";
        String passMock = "test pass";

        ReflectionTestUtils.setField(securityService, "authenticationManager", authenticationManager);
        ReflectionTestUtils.setField(securityService, "userDetailsService", userDetailsService);
        ReflectionTestUtils.setField(securityService, "logger", logger);

        UserDetails userDetails = Mockito.mock(UserDetails.class);
        UsernamePasswordAuthenticationToken tokenMock = Mockito.mock(UsernamePasswordAuthenticationToken.class);
        when(userDetailsService.loadUserByUsername(loginMock)).thenReturn(userDetails);

        when(securityService.createToken(passMock, userDetails)).thenReturn(tokenMock);
        when(tokenMock.isAuthenticated()).thenReturn(success);
        doCallRealMethod().when(securityService).autoLogin(loginMock, passMock);

        securityService.autoLogin(loginMock, passMock);

        verify(authenticationManager, times(1)).authenticate(tokenMock);
        verify(tokenMock, times(1)).setDetails(userDetails);

        if (success) {
            assertEquals(tokenMock, SecurityContextHolder.getContext().getAuthentication());
            verify(logger, times(1)).debug(String.format("Auto login %s successfully!", loginMock));
        } else {
            verify(logger, times(1)).error(String.format("login failed with '%s' login", loginMock));
        }
    }

    @Test
    public void createTokenTest() {
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        String passwordMock = "test password";

        when(securityService.createToken(passwordMock, userDetails)).thenCallRealMethod();

        UsernamePasswordAuthenticationToken token = securityService.createToken(passwordMock, userDetails);

        //could be refactor to assertThat ??
        Assert.assertTrue(token.getAuthorities().isEmpty());
        Assert.assertEquals(userDetails, token.getPrincipal());
        Assert.assertEquals(passwordMock, token.getCredentials());
    }

    /**
     * just to create logged in user for checking findLoggedInUsername
     */
    protected void login(String userName, String password) {
        ClientEntity user = new ClientEntity();
        user.setLogin(userName);
        user.setEncryptedPass(password);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        UserDetails userDetails = new User(user.getLogin(), user.getEncryptedPass(), grantedAuthorities);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            password,
            userDetails.getAuthorities()
        );
        usernamePasswordAuthenticationToken.setDetails(userDetails);

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
