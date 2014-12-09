package ru.qiwi.dao;

import ru.qiwi.model.Agent;
import ru.qiwi.utils.HashUtils;

import java.util.List;

public class AgentDAO extends AbstractDAO {


    public int createAgent(Agent agent) {
        String sql = "INSERT INTO agent (phone, password) VALUES(?, ?)";
        return getJdbcTemplate().update(sql, agent.getPhone(), agent.getPassword());
    }


    public Integer getByLogin(Agent agent) {
        String sql = "SELECT id FROM agent WHERE phone = ?";
        List<Integer> result = getJdbcTemplate().queryForList(sql, Integer.class, agent.getPhone());
        if (result.isEmpty()) return null;
        return result.get(0);
    }

    public Integer getByLoginAndPassword(Agent agent) {
        String sql = "SELECT id FROM agent WHERE phone = ? AND password = ?";
        String hashPass = HashUtils.hash(agent.getPassword());
        List<Integer> result = getJdbcTemplate().queryForList(sql, Integer.class, agent.getPhone(), hashPass);
        if (result.isEmpty()) return null;
        return result.get(0);
    }


}
