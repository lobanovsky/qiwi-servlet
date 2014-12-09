package ru.qiwi.services;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.qiwi.dao.AgentDAO;
import ru.qiwi.model.Agent;

@Transactional(readOnly = true)
public class AgentServiceImp implements AgentService {

    private AgentDAO dao;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int createAgent(Agent agent) {
        int id = dao.createAgent(agent);
        //допустим тут будут еще операции CRUD которые нужно выполнить в рамках одной транзакции
        return id;
    }

    @Override
    public Integer findIdByLogin(Agent agent) {
        return dao.getByLogin(agent);
    }

    @Override
    public Integer findIdByLoginAndPassword(Agent agent) {
        return dao.getByLoginAndPassword(agent);
    }

    public void setDao(AgentDAO dao) {
        this.dao = dao;
    }
}
