package ua.project.movie.theater.service;

import org.junit.Assert;
import org.junit.Test;
import ua.project.movie.theater.database.TicketDAO;
import ua.project.movie.theater.database.model.Ticket;
import ua.project.movie.theater.database.model.User;
import ua.project.movie.theater.exception.AppException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ua.project.movie.theater.service.MockData.*;

public class TicketServiceTest {
    class MockTicketDAO implements TicketDAO {

        @Override
        public Optional<Integer> buyTickets(Integer movieSessionId, List<Integer> seatIds, int userId) {
            return movieSessionId.equals(TEST_SESSION_1.getId()) ? Optional.of(seatIds.size()) : Optional.empty();
        }

        @Override
        public Optional<List<Ticket>> getTicketsForUserMovie(User user, Integer movieSessionId) {
            return user.getId() == TEST_USER.getId()
                    ? Optional.of(testTickets) : Optional.empty();
        }

        @Override
        public Long countAllSeatsBought(LocalDate localDate, LocalDate localDate1) {
            return localDate != null || localDate1 != null ? 10L : 0L;
        }

        @Override
        public Optional<List<Ticket>> getUserTickets(User user) {
            return user.getId() == TEST_USER.getId()
                    ? Optional.of(testTickets) : Optional.empty();
        }
    }

    private final TicketService ticketService = new TicketService(new MockTicketDAO());

    public final List<Ticket> testTickets = Stream.of(TEST_TICKET_1, TEST_TICKET_2).collect(Collectors.toList());
    public final List<Integer> testSeatsIds = Stream.of(TEST_SEAT_1.getId(), TEST_SEAT_2.getId()).collect(Collectors.toList());

    @Test
    public void buySeatSuccess() throws AppException {
        int result = ticketService.buySeat(TEST_SESSION_1.getId(), testSeatsIds, TEST_USER);
        Assert.assertEquals( 2, result);
    }

    @Test(expected = AppException.class)
    public void buySeatThrowsErrorIfNotBought() throws AppException {
        ticketService.buySeat(TEST_SESSION_2.getId(), testSeatsIds, TEST_USER);
    }

    @Test
    public void getUserTicketsForSessionTest() throws AppException {
        List<Ticket> ticketsReceived = ticketService.getUserTicketsForSession(TEST_USER, TEST_SESSION_1.getId());
        Assert.assertTrue(ticketsReceived.containsAll(testTickets));
    }

    @Test(expected = AppException.class)
    public void getUserTicketsForSessionThrowsExceptionIfNotFound() throws AppException {
        ticketService.getUserTicketsForSession(TEST_ADMIN, TEST_SESSION_1.getId());
    }

    @Test
    public void getStatsTest() throws AppException {
        Long expected = 5L;
        LocalDate start = LocalDate.of(2020, 2, 10);
        LocalDate end = LocalDate.of(2020, 2, 12);
        List<LocalDate> dates = Stream.of(start, end).collect(Collectors.toList());
        Assert.assertEquals(expected, ticketService.getStats(dates));
    }

    @Test(expected = AppException.class)
    public void getStatsThrowsExceptionIfNoDatesProvided() throws AppException {
        ticketService.getStats(null);
    }

    @Test
    public void getTicketsForCurrentUserTest() throws AppException {
        Assert.assertEquals(testTickets, ticketService.getTicketsForCurrentUser(TEST_USER));
    }

    @Test(expected = AppException.class)
    public void getTicketsForCurrentUserThrowsException() throws AppException {
       ticketService.getTicketsForCurrentUser(TEST_ADMIN);
    }
}