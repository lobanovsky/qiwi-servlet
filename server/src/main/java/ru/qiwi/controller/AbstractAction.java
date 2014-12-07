package ru.qiwi.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.qiwi.dao.AccountDAO;
import ru.qiwi.dao.AgentDAO;
import ru.qiwi.model.Agent;
import ru.qiwi.transport.ResultEnum;
import ru.qiwi.utils.XmlUtils;

import javax.servlet.AsyncContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractAction implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(AbstractAction.class);

    protected Agent agent;
    protected AgentDAO agentDAO;
    protected AccountDAO accountDAO;

    protected AsyncContext ctx;


    protected void sendResult(String res) {
        try {
            PrintWriter out = ctx.getResponse().getWriter();
            out.write(res);
            ctx.complete();
        } catch (IOException e) {
            logger.error("IOException", e);
            throw new RuntimeException(e);
        }
    }


    protected String validate(Agent agent) {
        if (!isValidPhone(agent.getPhone())) return XmlUtils.responseAgentToXml(ResultEnum.WRONG_LOGIN);
        if (isBadPassword(agent.getPassword())) return XmlUtils.responseAgentToXml(ResultEnum.BAD_PASSWORD);
        if (isExistAgent(agent)) return XmlUtils.responseAgentToXml(ResultEnum.DUPLICATE_AGENT);
        return StringUtils.EMPTY;
    }


    private boolean isValidPhone(String number) {
        Pattern pattern = Pattern.compile("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }


    private boolean isExistAgent(Agent agent) {
        return agentDAO.getByLogin(agent) != null;
    }


    private boolean isBadPassword(String password) {
        return StringUtils.length(password) < 5;
    }


}
