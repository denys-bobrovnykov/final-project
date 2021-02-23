package ua.project.movie.theater.commands.buy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.project.movie.theater.commands.Command;
import ua.project.movie.theater.database.DAOFactory;
import ua.project.movie.theater.database.model.Seat;
import ua.project.movie.theater.database.model.User;
import ua.project.movie.theater.exception.AppException;
import ua.project.movie.theater.service.MovieSessionService;
import ua.project.movie.theater.service.SeatService;
import ua.project.movie.theater.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

import static ua.project.movie.theater.commands.CommandUtility.*;

public class BuyTicketCommand implements Command {
    private final Logger logger = LogManager.getLogger(BuyTicketCommand.class);

    private final MovieSessionService movieSessionService = new MovieSessionService(DAOFactory.getDAOFactory().getMovieSessionDAO());
    private final SeatService seatService = new SeatService(DAOFactory.getDAOFactory().getSeatDAO());
    private final TicketService ticketService = new TicketService(DAOFactory.getDAOFactory().getTicketDAO());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Integer movieSessionId = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id")) : null;
        try {
            if (request.getSession().getAttribute("user") != null) {
                request.setAttribute("tickets", ticketService.getUserTicketsForSession(
                        (User) request.getSession().getAttribute("user"), movieSessionId)
                );
            }
            List<Seat> seatsFromDB = seatService.getAllSeatsFromDB();
            request.setAttribute("flash", copyFlash(request));
            request.setAttribute("selectedSession", movieSessionService.getSessionFromDbById(movieSessionId));
            request.setAttribute("seatsTotal", seatsFromDB.size());
            request.setAttribute("seatSession", seatService.getSeatsForSession(movieSessionId));
            request.setAttribute("rows", seatsFromDB.stream().collect(Collectors.groupingBy(Seat::getRow)));
        } catch (AppException ex) {
            logger.error("Did not get session and seats", ex);
        }
        return "/buyTicket.jsp";
    }
}
