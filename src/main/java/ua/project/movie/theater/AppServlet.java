package ua.project.movie.theater;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.project.movie.theater.commands.*;
import ua.project.movie.theater.commands.admin.AdminCommand;
import ua.project.movie.theater.commands.admin.AdminCommandSession;
import ua.project.movie.theater.commands.buy.BuyTicketCommand;
import ua.project.movie.theater.commands.details.SessionDetailsCommand;
import ua.project.movie.theater.commands.index.IndexCommand;
import ua.project.movie.theater.commands.login.LoginCommand;
import ua.project.movie.theater.commands.logout.LogOutCommand;
import ua.project.movie.theater.commands.registration.RegistrationCommand;
import ua.project.movie.theater.database.model.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppServlet extends HttpServlet {
    private static final Logger logger = LogManager.getRootLogger();
    private Map<String, Command> commands = new HashMap<>();

    public void init(ServletConfig servletConfig){
        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<User>());
        commands.put("home", new IndexCommand());
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogOutCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("exception" , new ExceptionCommand());
        commands.put("admin", new AdminCommand());
        commands.put("admin/sessions", new AdminCommandSession());
        commands.put("details", new SessionDetailsCommand());
        commands.put("buy", new BuyTicketCommand());
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("Start");
        String path = request.getRequestURI();
        logger.info(path);
        path = path.replaceAll(".*/app/" , "");
        Command command = commands.getOrDefault(path, (req, resp) -> req.getContextPath() + "redirect:/");
        String page = command.execute(request, response);
        if(page.contains("redirect:")){
            response.sendRedirect(page.replace("redirect:", "/app"));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
