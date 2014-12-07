package ru.qiwi.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.xml.sax.SAXException;
import ru.qiwi.dao.AccountDAO;
import ru.qiwi.dao.AgentDAO;
import ru.qiwi.model.Agent;
import ru.qiwi.transport.Request;
import ru.qiwi.utils.XmlUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "client", urlPatterns = "/clients")
public class DispatcherServlet extends HttpServlet {

    ApplicationContext applicationContext;

    @Override
    public void init() throws ServletException {
        if (applicationContext == null) {
            applicationContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.write("GET запрос!");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }


    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            Request req = XmlUtils.xmlToRequest(request.getInputStream());
            Agent agent = new Agent();
            agent.setPhone(req.getLogin());
            agent.setPassword(req.getPassword());

            AgentDAO agentDAO = applicationContext.getBean(AgentDAO.class);
            AccountDAO accountDAO = applicationContext.getBean(AccountDAO.class);

            String res = StringUtils.EMPTY;
            switch (req.getRequestType()) {
                case NEW_AGT: {
                    res = new NewAgtAction(agent, agentDAO).action();
                    break;
                }
                case AGT_BAL: {
                    res = new AgtBalAction(agent, agentDAO, accountDAO).action();
                    break;
                }
            }
            response.setContentType("application/xml; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.write(res);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}