package com.kpi.payments.service;

import com.kpi.payments.dao.UserDao;
import com.kpi.payments.domain.User;

import java.util.Optional;

public class UserService {

    private final UserDao dao = new UserDao();

    public Optional<User> getByEmailAndPassword(String email, String password) {
        return dao.getByEmailAndPassword(email, password);
    }
}
