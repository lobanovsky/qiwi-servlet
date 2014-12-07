package ru.qiwi.controller;

import org.apache.commons.lang3.StringUtils;
import ru.qiwi.dao.AgentDAO;
import ru.qiwi.model.Agent;
import ru.qiwi.transport.ResultEnum;
import ru.qiwi.utils.HashUtils;
import ru.qiwi.utils.XmlUtils;

public class NewAgtAction extends AbstractAction {


    public NewAgtAction(Agent agent, AgentDAO agentDAO) {
        this.agent = agent;
        this.agentDAO = agentDAO;
    }


    @Override
    protected String action() {
        String validateResult = validate(agent);
        if (StringUtils.isNotBlank(validateResult)) return validateResult;
        agent.setPassword(HashUtils.hash(agent.getPassword()));
        int i = agentDAO.createAgent(agent);
        if (i == 0) return XmlUtils.responseAgentToXml(ResultEnum.OTHER);
        return XmlUtils.responseAgentToXml(ResultEnum.OK);
    }

}

