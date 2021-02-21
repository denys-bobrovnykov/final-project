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
        Integer id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id")) : null;
        if ("POST".equals(request.getMethod())) {
            String[] seatId = getParams(request, "seat_id");
            if (seatId.length == 0) {
                logger.warn("No seat selected on post");
                getFlashAttributesContainer(request).put("error", "error.choose.seat");
                return request.getContextPath() + "redirect:/buy" + request
                        .getQueryString().substring("?locale=mm".length()) + "id=" + id;
            }
            try {
                ticketService.buySeat(id, seatId,
                        (User) request.getSession().getAttribute("user"));
            } catch (AppException ex) {
                logger.error("Buy ticket(s) transaction failed", ex);
                return request.getContextPath() + "redirect:/buy?locale=" + request.getSession().getAttribute("currentLocale") + "&" + request
                        .getQueryString();
            }
            return request.getContextPath() + "redirect:/buy?locale=" + request.getSession().getAttribute("currentLocale") + "&" + request
                    .getQueryString();
        }

        try {
            request.setAttribute("tickets", ticketService.getUserTicketsForSession(
                    (User) request.getSession().getAttribute("user"), id)
            );
            List<Seat> seatsFromDB = seatService.getAllSeatsFromDB();
            request.setAttribute("flash", copyFlash(request));
            request.setAttribute("selectedSession", movieSessionService.getSessionFromDbById(id));
            request.setAttribute("seatsTotal", seatsFromDB.size());
            request.setAttribute("seatSession", seatService.getSeatsForSession(id));
            request.setAttribute("rows", seatsFromDB.stream().collect(Collectors.groupingBy(Seat::getRow)));
        } catch (AppException ex) {
            logger.error("Did not get session and seats", ex);
        }
        return "/buyTicket.jsp";
    }
}
