package ua.project.movie.theater.commands.buy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.project.movie.theater.commands.Command;
import ua.project.movie.theater.database.DAOFactory;
import ua.project.movie.theater.database.model.User;
import ua.project.movie.theater.exception.AppException;
import ua.project.movie.theater.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ua.project.movie.theater.commands.CommandUtility.*;

public class BuyTicketPurchaseCommand implements Command {
    private final Logger logger = LogManager.getLogger(BuyTicketPurchaseCommand.class);

    private final TicketService ticketService = new TicketService(DAOFactory.getDAOFactory().getTicketDAO());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Integer movieSessionId = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id")) : null;
            String[] seatId = getParams(request, "seat_id");
            List<Integer> mappedSeatId = Arrays.stream(seatId).map(Integer::parseInt).collect(Collectors.toList());
            if (seatId.length == 0) {
                logger.warn("No seat selected on post");
                getFlashAttributesContainer(request).put("error", "error.choose.seat");
                return request.getContextPath() + "redirect:/buy?" + request
                        .getQueryString().replace("?locale=mm", "");
            }
            try {
                ticketService.buySeat(movieSessionId, mappedSeatId,
                        (User) request.getSession().getAttribute("user"));
            } catch (AppException ex) {
                logger.error("Buy ticket(s) transaction failed", ex);
                return request.getContextPath() + "redirect:/buy?locale=" + request.getSession().getAttribute("currentLocale") + "&" + request
                        .getQueryString();
            }
            return request.getContextPath() + "redirect:/buy?locale=" + request.getSession().getAttribute("currentLocale") + "&" + request
                    .getQueryString();
    }
}
