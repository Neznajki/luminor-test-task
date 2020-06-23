package com.luminor.task.payment.security;

import com.luminor.task.payment.db.entity.ClientEntity;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.Errors;

import static org.mockito.Mockito.*;

@SpringBootTest
public class UserValidatorTest {
    public static Object[][] dataProvider() {
        return new Object[][]{
            {/* login */"login", /* loginValid */false, /* password */"password", /* passwordValid */true, /* passwordReset */true, /* existingClientEntity */null, /* clientEntityValid */false, /* passwordRepeat */"pass", /* passwordRepeatValid */false},
            {/* login */"valid login", /* loginValid */true, /* password */"password", /* passwordValid */true, /* passwordReset */true, /* existingClientEntity */new ClientEntity(), /* clientEntityValid */true, /* passwordRepeat */"password", /* passwordRepeatValid */true},
            {/* login */"login", /* loginValid */false, /* password */"password", /* passwordValid */true, /* passwordReset */false, /* existingClientEntity */new ClientEntity(), /* clientEntityValid */false, /* passwordRepeat */"pass", /* passwordRepeatValid */false},
            {/* login */"login", /* loginValid */false, /* password */"password", /* passwordValid */true, /* passwordReset */true, /* existingClientEntity */null, /* clientEntityValid */false, /* passwordRepeat */"pass", /* passwordRepeatValid */false},
            {/* login */"login", /* loginValid */false, /* password */"password", /* passwordValid */true, /* passwordReset */false, /* existingClientEntity */null, /* clientEntityValid */true, /* passwordRepeat */"pass", /* passwordRepeatValid */false},
            {/* login */"valid login", /* loginValid */true, /* password */"pas", /* passwordValid */false, /* passwordReset */true, /* existingClientEntity */null, /* clientEntityValid */false, /* passwordRepeat */"pas", /* passwordRepeatValid */true}
            //... there could be tons of those things
            //additional and better solution would be to create test case generators, just spam into some object all possible differences and then generate object for each combination of them
        };
    }

    @ParameterizedTest
    @MethodSource(value = "dataProvider")
    public void validateTest(
        String login,
        Boolean loginValid,
        String password,
        Boolean passwordValid,
        Boolean passwordReset,
        ClientEntity clientEntity,
        Boolean isClientEntityValid,
        String passwordConfirm,
        Boolean isPasswordConfirmValid
    ) {
        User user = new User();

        user.setLogin(login);
        user.setPassword(password);
        user.setClientEntity(clientEntity);
        user.setPasswordReset(passwordReset);
        user.setPasswordConfirm(passwordConfirm);

        UserValidator userValidator = new UserValidator();
        Errors errors = Mockito.mock(Errors.class);

        userValidator.validate(user, errors);

        InOrder inOrder = inOrder(errors);

        if (! loginValid) {
            inOrder.verify(errors).rejectValue(eq("login"), eq("Size.userForm.login"));
        }

        if (! isClientEntityValid) {
            if (passwordReset) {
                inOrder.verify(errors).rejectValue("passwordReset", "NotExists.userForm.login");
            } else {
                inOrder.verify(errors).rejectValue("passwordReset", "Duplicate.userForm.login");
            }
        }

        if (! passwordValid) {
            inOrder.verify(errors).rejectValue(eq("password"), eq("Size.userForm.password"));
        }

        if (! isPasswordConfirmValid) {
            inOrder.verify(errors).rejectValue(eq("passwordConfirm"), eq("Diff.userForm.passwordConfirm"));
        }
    }
}
