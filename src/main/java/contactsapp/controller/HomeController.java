package contactsapp.controller;

import contactsapp.command.Command;
import contactsapp.command.CommandManager;
import contactsapp.utils.schedule.ScheduleManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.SchedulerException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/contacts/*")
@MultipartConfig
public class HomeController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(HomeController.class);

    private CommandManager commandManager;
    private ScheduleManager scheduleManager;
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = commandManager.getGETCommand(req.getRequestURI());
        command.execute(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = commandManager.getPOSTCommand(req.getRequestURI());
        command.execute(req,resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = commandManager.getPUTCommand(req.getRequestURI());
        command.execute(req,resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = commandManager.getDELETECommand(req.getRequestURI());
        command.execute(req,resp);
    }


    @Override
    public void destroy() {
        try {
            scheduleManager.shutDown();
        } catch (SchedulerException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void init() throws ServletException {
        commandManager = new CommandManager();
        try {
            scheduleManager = new ScheduleManager();
        } catch (SchedulerException e) {
            LOGGER.error(e);
        }
    }
}
