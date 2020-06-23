package com.luminor.task.payment.security;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        if (user.getLogin().length() < 6 || user.getLogin().length() > 64) {
            errors.rejectValue("login", "Size.userForm.login");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "NotEmpty");

        if (! user.getPasswordReset() && user.getClientEntity() != null) {
            errors.rejectValue("passwordReset", "Duplicate.userForm.login");
        }

        if (user.getPasswordReset() && user.getClientEntity() == null) {
            errors.rejectValue("passwordReset", "NotExists.userForm.login");
        }

        if (user.getPassword().length() < 4) {
            errors.rejectValue("password", "Size.userForm.password");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");

        if (! user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "NotEmpty");
    }
}
