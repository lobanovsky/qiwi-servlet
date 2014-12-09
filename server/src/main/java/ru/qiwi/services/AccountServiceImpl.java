package ru.qiwi.services;

import ru.qiwi.dao.AccountDAO;
import ru.qiwi.model.Account;

public class AccountServiceImpl implements AccountService {

    private AccountDAO dao;

    @Override
    public Account findByAgentId(int id) {
        return dao.findAccount(id);
    }

    public void setDao(AccountDAO dao) {
        this.dao = dao;
    }
}
