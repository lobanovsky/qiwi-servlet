package ru.qiwi.controller;

import org.apache.commons.lang3.StringUtils;
import ru.qiwi.model.Agent;
import ru.qiwi.services.AgentService;
import ru.qiwi.transport.ResultEnum;
import ru.qiwi.utils.HashUtils;
import ru.qiwi.utils.XmlUtils;

import javax.servlet.AsyncContext;

public class NewAgtAction extends AbstractAction {


    public NewAgtAction(AsyncContext ctx, Agent agent, AgentService agentService) {
        this.ctx = ctx;
        this.agent = agent;
        this.agentService = agentService;
    }


    @Override
    public void run() {
        longProcessing(5000);

        String validateResult = validate(agent);
        if (StringUtils.isNotBlank(validateResult)) {
            sendResult(validateResult);
            return;
        }
        agent.setPassword(HashUtils.hash(agent.getPassword()));
        int i = agentService.createAgent(agent);
        if (i == 0) {
            sendResult(XmlUtils.responseAgentToXml(ResultEnum.OTHER));
            return;
        }
        sendResult(XmlUtils.responseAgentToXml(ResultEnum.OK));
    }

    private void longProcessing(int secs) {
        try {
            Thread.sleep(secs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

