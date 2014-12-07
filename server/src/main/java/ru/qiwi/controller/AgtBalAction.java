package ru.qiwi.controller;

import ru.qiwi.dao.AccountDAO;
import ru.qiwi.dao.AgentDAO;
import ru.qiwi.model.Account;
import ru.qiwi.model.Agent;
import ru.qiwi.transport.ResultEnum;
import ru.qiwi.utils.XmlUtils;

import javax.servlet.AsyncContext;

public class AgtBalAction extends AbstractAction {


    public AgtBalAction(AsyncContext ctx, Agent agent, AgentDAO agentDAO, AccountDAO accountDAO) {
        this.ctx = ctx;
        this.agent = agent;
        this.agentDAO = agentDAO;
        this.accountDAO = accountDAO;
    }


    @Override
    public void run() {
        Integer id = agentDAO.getByLogin(agent);
        if (id == null) {
            sendResult(XmlUtils.responseAccountToXml(ResultEnum.AGENT_NOT_EXITS));
            return;
        }
        Account account = accountDAO.findAccount(id);
        if (account == null) {
            sendResult(XmlUtils.responseAccountToXml(ResultEnum.ACCOUNT_NOT_EXITS));
            return;
        }
        sendResult(XmlUtils.responseAccountToXml(ResultEnum.OK, account.getAmount()));
    }
}
