package ru.qiwi.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.qiwi.model.Account;

import java.sql.ResultSet;
import java.sql.SQLException;


public class AccountRowMap implements RowMapper<Account> {

    @Override
    public Account mapRow(ResultSet rs, int i) throws SQLException {
        Account account = new Account();
        account.setId(rs.getInt("id"));
        account.setClientId(rs.getInt("agent_id"));
        account.setAmount(rs.getBigDecimal("amount"));
        return account;
    }

}
