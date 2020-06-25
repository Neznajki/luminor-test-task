package com.luminor.task.payment.web.rest;

import com.luminor.task.payment.contract.SecurityService;
import com.luminor.task.payment.contract.UserService;
import com.luminor.task.payment.db.repository.ClientRepository;
import com.luminor.task.payment.security.User;
import com.luminor.task.payment.security.UserValidator;
import com.luminor.task.payment.web.ClientController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private SecurityService securityService;
    @MockBean
    private UserValidator userValidator;
    @MockBean
    private ClientRepository clientRepository;//just to reduce error on application boot

    @Test
    public void registrationPageTest() throws Exception {
        this.mockMvc.perform(get("/registration")).andDo(print()).andExpect(status().isOk())
            .andExpect(forwardedUrl("/WEB-INF/jsp/registration.jsp"))
            .andExpect(model().attribute("userForm", equalTo(new User())));
    }

    @Test
    public void registrationErrorTest() throws Exception {
        User user = new User();
        String urlTemplate = "/registration";

        doAnswer(invocationOnMock -> {
            BindingResult bindingResult = invocationOnMock.getArgument(1);
            bindingResult.rejectValue("login", "test_value");

            return null;
        }).when(userValidator).validate(any(User.class), any(BindingResult.class));

        this.mockMvc.perform(post(urlTemplate).flashAttr("userForm", user))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(forwardedUrl("/WEB-INF/jsp/registration.jsp"));

        verify(userService, never()).save(any());
        verify(securityService, never()).autoLogin(anyString(), anyString());
    }

    @Test
    public void registrationSuccessTest() throws Exception {
        User user = new User();
        user.setLogin("test login");
        user.setPassword("test password");
        String urlTemplate = "/registration";

        when(userService.findByUsername(any(String.class))).thenReturn(null);

        this.mockMvc.perform(post(urlTemplate).flashAttr("userForm", user))
            .andDo(print()).andExpect(status().isFound())
            .andExpect(redirectedUrl("/"));

        verify(userService, times(1)).save(eq(user));
        verify(securityService, times(1)).autoLogin(eq(user.getLogin()), eq(user.getPassword()));
    }

    @Test
    public void loginErrorTest() throws Exception {
        this.mockMvc.perform(
            get("/login").param("error", "test")
        ).andDo(print()).andExpect(status().isOk())
            .andExpect(model().attribute("error", "Your username and password is invalid."))
            .andExpect(model().attributeDoesNotExist("message"))
            .andExpect(forwardedUrl("/WEB-INF/jsp/login.jsp"));
    }

    @Test
    public void loginLogoutTest() throws Exception {
        this.mockMvc.perform(
            get("/login").param("logout", "test")
        ).andDo(print()).andExpect(status().isOk())
            .andExpect(model().attribute("message", "You have been logged out successfully."))
            .andExpect(model().attributeDoesNotExist("error"))
            .andExpect(forwardedUrl("/WEB-INF/jsp/login.jsp"));
    }

    @Test
    public void loginTest() throws Exception {
        this.mockMvc.perform(
            get("/login")
        ).andDo(print()).andExpect(status().isOk())
            .andExpect(model().attributeDoesNotExist("message"))
            .andExpect(model().attributeDoesNotExist("error"))
            .andExpect(forwardedUrl("/WEB-INF/jsp/login.jsp"));
    }
}
