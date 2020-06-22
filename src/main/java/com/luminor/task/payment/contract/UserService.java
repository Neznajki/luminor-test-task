package com.luminor.task.payment.contract;

import com.luminor.task.payment.db.entity.ClientEntity;
import com.luminor.task.payment.security.User;

public interface UserService {
    void save(User user);

    ClientEntity findByUsername(String username);
}
