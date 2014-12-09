package ru.qiwi.dao;

import ru.qiwi.model.Account;

import java.util.List;

public class AccountDAO extends AbstractDAO {

    public Account findAccount(int agentId) {
        String sql = "SELECT * FROM account WHERE agent_id = ?";
        List<Account> accountList = getJdbcTemplate().query(sql, new AccountRowMap(), agentId);
        if (accountList.isEmpty()) return null;
        return accountList.get(0);
    }

}
