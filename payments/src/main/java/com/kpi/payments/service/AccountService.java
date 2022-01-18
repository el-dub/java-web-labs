package com.kpi.payments.service;

import com.kpi.payments.dao.AccountDao;
import com.kpi.payments.domain.Account;

import java.util.List;
import java.util.Optional;

public class AccountService {

    private final AccountDao accountDao = new AccountDao();

    public Optional<Account> getById(long id) {
        return accountDao.getById(id);
    }

    public List<Account> getAll() {
        return accountDao.getAll();
    }

    public List<Account> getAllByUserId(long userId) {
        return accountDao.getAllByUserId(userId);
    }

    public void update(Account account) {
        accountDao.update(account);
    }
}
