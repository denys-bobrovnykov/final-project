package ua.project.movie.theater.database;

import ua.project.movie.theater.database.model.Ticket;
import ua.project.movie.theater.database.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * TicketDAO interface.
 */
public interface TicketDAO {

    /**
     * Buy tickets action
     * @param movieSessionId movie session id
     * @param seatIds seats ids array
     * @param userId user id
     * @return
     */
    Optional<Integer> buyTickets(Integer movieSessionId, List<Integer> seatIds, int userId);

    /**
     * Gets tickets for matching movie and user
     * @param user User model
     * @param id Integer movie id
     * @return Optional with list of Ticket models
     */
    Optional<List<Ticket>> getTicketsForUserMovie(User user, Integer id);

    /**
     * Counts all tickets bought between specified dates
     * @param localDate start date
     * @param localDate1 end date
     * @return list of Ticket models
     */
    Long countAllSeatsBought(LocalDate localDate, LocalDate localDate1);

    /**
     * Gets all tickets belongs to user
     * @param user User model
     * @return list if Ticket models
     */
    Optional<List<Ticket>> getUserTickets(User user);
}
