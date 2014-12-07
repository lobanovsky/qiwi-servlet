package ru.qiwi.controller;

import ru.qiwi.dao.AccountDAO;
import ru.qiwi.dao.AgentDAO;
import ru.qiwi.model.Account;
import ru.qiwi.model.Agent;
import ru.qiwi.transport.ResultEnum;
import ru.qiwi.utils.XmlUtils;

public class AgtBalAction extends AbstractAction {


    public AgtBalAction(Agent agent, AgentDAO agentDAO, AccountDAO accountDAO) {
        this.agent = agent;
        this.agentDAO = agentDAO;
        this.accountDAO = accountDAO;
    }


    @Override
    protected String action() {
        Integer id = agentDAO.getByLogin(agent);
        if (id == null) return XmlUtils.responseAccountToXml(ResultEnum.AGENT_NOT_EXITS);
        Account account = accountDAO.findAccount(id);
        if (account == null) return XmlUtils.responseAccountToXml(ResultEnum.ACCOUNT_NOT_EXITS);
        return XmlUtils.responseAccountToXml(ResultEnum.OK, account.getAmount());
    }

}
