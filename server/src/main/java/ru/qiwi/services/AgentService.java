package ru.qiwi.services;

import ru.qiwi.model.Agent;

public interface AgentService {

    public int createAgent(Agent agent);

    public Integer findIdByLogin(Agent agent);

    public Integer findIdByLoginAndPassword(Agent agent);

}
