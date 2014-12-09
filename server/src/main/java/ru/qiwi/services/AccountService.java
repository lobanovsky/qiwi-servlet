package ru.qiwi.services;

import ru.qiwi.model.Account;

public interface AccountService {

    public Account findByAgentId(int id);

}
