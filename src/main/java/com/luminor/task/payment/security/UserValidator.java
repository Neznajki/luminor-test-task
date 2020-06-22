package com.luminor.task.payment.security;

import com.luminor.task.payment.contract.UserService;
import com.luminor.task.payment.db.entity.ClientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "NotEmpty");
        if (user.getLogin().length() < 6 || user.getLogin().length() > 64) {
            errors.rejectValue("login", "Size.userForm.login");
        }

        ClientEntity client = userService.findByUsername(user.getLogin());
        if (! user.getPasswordReset() && client != null) {
            errors.rejectValue("login", "Duplicate.userForm.login");
        }

        if (user.getPasswordReset() && client == null) {
            errors.rejectValue("login", "NotExists.userForm.login");
        }

        user.setClientEntity(client);//just to use only single select when you need to change password
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 4) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}
