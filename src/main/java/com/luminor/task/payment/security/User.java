package com.luminor.task.payment.security;

import com.luminor.task.payment.db.entity.ClientEntity;

import java.util.Objects;

public class User {
    private String login;
    private String password;
    private String passwordConfirm;
    private Boolean passwordReset;
    private ClientEntity clientEntity;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Boolean getPasswordReset() {
        return passwordReset;
    }

    public void setPasswordReset(Boolean passwordReset) {
        this.passwordReset = passwordReset;
    }

    public ClientEntity getClientEntity() {
        return clientEntity;
    }

    public void setClientEntity(ClientEntity clientEntity) {
        this.clientEntity = clientEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) &&
            Objects.equals(password, user.password) &&
            Objects.equals(passwordConfirm, user.passwordConfirm) &&
            Objects.equals(passwordReset, user.passwordReset) &&
            Objects.equals(clientEntity, user.clientEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, passwordConfirm, passwordReset, clientEntity);
    }
}
