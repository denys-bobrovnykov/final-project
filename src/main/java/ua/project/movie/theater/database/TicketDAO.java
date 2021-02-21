package ua.project.movie.theater.database;

import ua.project.movie.theater.database.model.Ticket;
import ua.project.movie.theater.database.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TicketDAO {

    Optional<Integer> buyTickets(Integer movieSessionId, String[] seatIds, int userId);

    Optional<List<Ticket>> getTicketsForUserMovie(User user, Integer id);

    Long countAllSeatsBought(LocalDate localDate, LocalDate localDate1);

    Optional<List<Ticket>> getUserTickets(User user);
}
