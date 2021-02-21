package ua.project.movie.theater.service;

import ua.project.movie.theater.database.TicketDAO;
import ua.project.movie.theater.database.model.Ticket;
import ua.project.movie.theater.database.model.User;
import ua.project.movie.theater.exception.AppException;

import java.util.List;

public class TicketService {
    private TicketDAO ticketDAO;
    public TicketService(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    public Integer buySeat(Integer id, String[] seatId, User user) throws AppException {
        return ticketDAO.buyTickets(id, seatId, user.getId()).orElseThrow(() -> new AppException("Unable to buy"));
    }

    public List<Ticket> getUserTicketsForSession(User user, Integer id) throws AppException {
        return ticketDAO.getTicketsForUserMovie(user, id).orElseThrow(() -> new AppException("Tickets not found"));
    }
}
