package ua.project.movie.theater.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.project.movie.theater.database.TicketDAO;
import ua.project.movie.theater.database.model.Ticket;
import ua.project.movie.theater.database.model.User;
import ua.project.movie.theater.exception.AppException;

import java.time.LocalDate;
import java.util.List;

/**
 * Ticket service.
 */
public class TicketService {
    private final Logger logger = LogManager.getLogger(TicketService.class);
    private final TicketDAO ticketDAO;

    public TicketService(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    public Integer buySeat(Integer id, List<Integer> seatId, User user) throws AppException {
        logger.info("User {} trying to buy tickets with ids {}", user.getEmail(), seatId);
        return ticketDAO.buyTickets(id, seatId, user.getId()).orElseThrow(() -> new AppException("Unable to buy"));
    }

    public List<Ticket> getUserTicketsForSession(User user, Integer movieSessionId) throws AppException {
        logger.info("Getting all user {} tickets for selected movie session", user.getEmail());
        return ticketDAO.getTicketsForUserMovie(user, movieSessionId).orElseThrow(() -> new AppException("Tickets not found"));
    }

    /**
     * Calculates average bought tickets per day based on selected period.
     * @param dates start and end dates
     * @return Long number
     * @throws AppException in case of exception in persistence layer
     */
    public Long getStats(List<LocalDate> dates) throws AppException {
        try {
            logger.info("Getting stats for period from {} to {}",  dates.get(0), dates.get(1));
            Long daysCount = Math.abs(dates.get(0).toEpochDay() - dates.get(1).toEpochDay());
            return daysCount == 0
                    ? ticketDAO.countAllSeatsBought(dates.get(0), dates.get(1))
                    : ticketDAO.countAllSeatsBought(dates.get(0), dates.get(1)) / daysCount;
        } catch (Exception ex) {
            throw new AppException("Could not get stats", ex);
        }
    }

    public List<Ticket> getTicketsForCurrentUser(User user) throws AppException {
        logger.info("Getting tickets for user: {}", user.getEmail());
        return ticketDAO.getUserTickets(user).orElseThrow(() -> new AppException("Could not get tickets for user"));
    }
}
