package com.luminor.task.payment.contract;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
