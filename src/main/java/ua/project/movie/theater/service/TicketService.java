package ua.project.movie.theater.service;

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
    private final TicketDAO ticketDAO;

    public TicketService(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    public Integer buySeat(Integer id, String[] seatId, User user) throws AppException {
        return ticketDAO.buyTickets(id, seatId, user.getId()).orElseThrow(() -> new AppException("Unable to buy"));
    }

    public List<Ticket> getUserTicketsForSession(User user, Integer id) throws AppException {
        return ticketDAO.getTicketsForUserMovie(user, id).orElseThrow(() -> new AppException("Tickets not found"));
    }

    /**
     * Calculates average bought tickets per day based on selected period.
     * @param dates start and end dates
     * @return Long number
     * @throws AppException in case of exception in persistence layer
     */
    public Long getStats(List<LocalDate> dates) throws AppException {
        Long daysCount = Math.abs(dates.get(0).toEpochDay() - dates.get(1).toEpochDay());
        try {
            return daysCount == 0
                    ? ticketDAO.countAllSeatsBought(dates.get(0), dates.get(1))
                    : ticketDAO.countAllSeatsBought(dates.get(0), dates.get(1)) / daysCount;
        } catch (Exception ex) {
            throw new AppException("Could not get stats from db", ex);
        }
    }

    public List<Ticket> getTicketsForCurrentUser(User user) throws AppException {
        return ticketDAO.getUserTickets(user).orElseThrow(() -> new AppException("Could not get tickets for user"));
    }
}
