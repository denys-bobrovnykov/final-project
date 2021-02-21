package ua.project.movie.theater.service;

import org.junit.Test;
import ua.project.movie.theater.database.TicketDAO;
import ua.project.movie.theater.database.model.Ticket;
import ua.project.movie.theater.database.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class TicketServiceTest {
    class MockTicketDAO implements TicketDAO {

        @Override
        public Optional<Integer> buyTickets(Integer movieSessionId, String[] seatIds, int userId) {
            return Optional.empty();
        }

        @Override
        public Optional<List<Ticket>> getTicketsForUserMovie(User user, Integer id) {
            return Optional.empty();
        }

        @Override
        public Long countAllSeatsBought(LocalDate localDate, LocalDate localDate1) {
            return null;
        }

        @Override
        public Optional<List<Ticket>> getUserTickets(User user) {
            return Optional.empty();
        }
    }

    private final TicketService ticketService = new TicketService(new MockTicketDAO());

    @Test
    public void buySeat() {
    }

    @Test
    public void getUserTicketsForSession() {
    }

    @Test
    public void getStats() {
    }

    @Test
    public void getTicketsForCurrentUser() {
    }
}