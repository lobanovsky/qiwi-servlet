package ru.qiwi.dao;

import org.springframework.transaction.annotation.Transactional;
import ru.qiwi.model.Agent;

import java.util.List;

public class AgentDAO extends AbstractDAO {


    @Transactional
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


}
