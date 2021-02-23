package ua.project.movie.theater.commands.cabinet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.project.movie.theater.commands.Command;
import ua.project.movie.theater.database.DAOFactory;
import ua.project.movie.theater.database.model.User;
import ua.project.movie.theater.exception.AppException;
import ua.project.movie.theater.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CabinetCommand implements Command {
    private final Logger logger = LogManager.getLogger(CabinetCommand.class);

    private final TicketService ticketService;

    public CabinetCommand() {
        ticketService = new TicketService(DAOFactory.getDAOFactory().getTicketDAO());
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("User {} entered cabinet", ((User)request.getSession().getAttribute("user")).getEmail());
        try {
            request.setAttribute("tickets", ticketService.getTicketsForCurrentUser((User) request.getSession().getAttribute("user")));
        } catch (AppException ex) {
            logger.error("Error while getting tickets from DB", ex);
            return request.getContextPath() + "redirect:/home";
        }
        return "/cabinet.jsp";
    }
}
