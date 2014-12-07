package ru.qiwi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.qiwi.dao.AccountDAO;
import ru.qiwi.dao.AgentDAO;
import ru.qiwi.model.Agent;
import ru.qiwi.transport.Request;
import ru.qiwi.utils.XmlUtils;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ThreadPoolExecutor;


@WebServlet(name = "client", urlPatterns = "/clients", asyncSupported = true)
public class DispatcherServlet extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private ApplicationContext applicationContext;
    private AgentDAO agentDAO;
    private AccountDAO accountDAO;


    @Override
    public void init() throws ServletException {
        if (applicationContext == null) {
            applicationContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
            agentDAO = applicationContext.getBean(AgentDAO.class);
            accountDAO = applicationContext.getBean(AccountDAO.class);
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
            long startTime = System.currentTimeMillis();
            logger.warn("AsyncLongRunningServlet Start::Name="
                    + Thread.currentThread().getName() + "::ID="
                    + Thread.currentThread().getId());

            Request req = XmlUtils.xmlToRequest(request.getInputStream());
            Agent agent = new Agent();
            agent.setPhone(req.getLogin());
            agent.setPassword(req.getPassword());

            request.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);

            AsyncContext aCtx = request.startAsync(request, response);
            ThreadPoolExecutor executor = (ThreadPoolExecutor) request.getServletContext().getAttribute("executor");
            switch (req.getRequestType()) {
                case NEW_AGT: {
                    AbstractAction action = new NewAgtAction(aCtx, agent, agentDAO);
                    executor.execute(action);
                    break;
                }
                case AGT_BAL: {
                    AbstractAction action = new AgtBalAction(aCtx, agent, agentDAO, accountDAO);
                    executor.execute(action);
                    break;
                }
            }

            long endTime = System.currentTimeMillis();
            logger.warn("AsyncLongRunningServlet End::Name="
                    + Thread.currentThread().getName() + "::ID="
                    + Thread.currentThread().getId() + "::Time Taken="
                    + (endTime - startTime) + " ms.");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}