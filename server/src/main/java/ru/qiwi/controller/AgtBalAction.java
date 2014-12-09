package ru.qiwi.controller;

import ru.qiwi.model.Account;
import ru.qiwi.model.Agent;
import ru.qiwi.services.AccountService;
import ru.qiwi.services.AgentService;
import ru.qiwi.transport.ResultEnum;
import ru.qiwi.utils.XmlUtils;

import javax.servlet.AsyncContext;

public class AgtBalAction extends AbstractAction {


    public AgtBalAction(AsyncContext ctx, Agent agent, AgentService agentService, AccountService accountService) {
        this.ctx = ctx;
        this.agent = agent;
        this.agentService = agentService;
        this.accountService = accountService;
    }


    @Override
    public void run() {
        Integer id = agentService.findIdByLoginAndPassword(agent);
        if (id == null) {
            sendResult(XmlUtils.responseAccountToXml(ResultEnum.AGENT_NOT_EXITS));
            return;
        }
        Account account = accountService.findByAgentId(id);
        if (account == null) {
            sendResult(XmlUtils.responseAccountToXml(ResultEnum.ACCOUNT_NOT_EXITS));
            return;
        }
        sendResult(XmlUtils.responseAccountToXml(ResultEnum.OK, account.getAmount()));
    }
}
