package ua.project.movie.theater.database.mysql;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.project.movie.theater.TestConnectionPool;
import ua.project.movie.theater.database.model.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ua.project.movie.theater.service.MockData.*;
import static ua.project.movie.theater.service.MockData.TEST_SESSION_2;

public class MySqlSeatDAOTest {

    private static final MySqlSeatDAO mySqlSeatDAO = new MySqlSeatDAO(TestConnectionPool.getInstance());
    private static final MySqlMovieSessionDAO mySqlMovieSessionDAO = new MySqlMovieSessionDAO(TestConnectionPool.getInstance());
    private static final MySqlMovieDAO mySqlMovieDAO = new MySqlMovieDAO(TestConnectionPool.getInstance());
    private static final MySqlTicketDAO mySqlTicketDAO = new MySqlTicketDAO(TestConnectionPool.getInstance());
    private static final MySqlUserDAO mySqlUserDAO = new MySqlUserDAO(TestConnectionPool.getInstance());
    private static MovieSession movieSession1;
    private static MovieSession movieSession2;
    private static Movie movie1;
    private static Movie movie2;
    private static Seat seat1;
    private static Seat seat2;
    private static User user;

    @BeforeClass
    public static void populateDatabase() {
        user = mySqlUserDAO.save(TEST_USER).orElse(null);
        movie1 = mySqlMovieDAO.save(TEST_MOVIE_1).orElse(null);
        movie2 = mySqlMovieDAO.save(TEST_MOVIE_2).orElse(null);
        seat1 = mySqlSeatDAO.save(TEST_SEAT_1).orElse(null);
        seat2 = mySqlSeatDAO.save(TEST_SEAT_2).orElse(null);
        movieSession1 = mySqlMovieSessionDAO.save(MovieSession.builder()
                .dayOfSession(TEST_SESSION_1.getDayOfSession())
                .timeStart(TEST_SESSION_1.getTimeStart()).movie(movie1).build()).orElse(null);
        movieSession2 = mySqlMovieSessionDAO.save(MovieSession.builder()
                .dayOfSession(TEST_SESSION_2.getDayOfSession())
                .timeStart(TEST_SESSION_2.getTimeStart()).movie(movie2).build()).orElse(null);
        mySqlTicketDAO.buyTickets(movieSession1.getId(),
                Stream.of(seat1.getId(), seat2.getId()).collect(Collectors.toList()), user.getId());
    }
    @AfterClass
    public static void clearDatabase() {
        mySqlMovieSessionDAO.delete(movieSession1);
        mySqlMovieSessionDAO.delete(movieSession2);
        mySqlMovieDAO.delete(movie1);
        mySqlMovieDAO.delete(movie2);
        mySqlSeatDAO.delete(seat1);
        mySqlSeatDAO.delete(seat2);
        mySqlUserDAO.delete(user);
    }

    @Test
    public void findOne() {
        Assert.assertEquals(seat1 , mySqlSeatDAO.findOne(seat1).orElse(null));
    }

    @Test
    public void findAll() {
        mySqlSeatDAO.save(seat1);
        mySqlSeatDAO.save(seat2);
        List<Seat> seatList = Stream.of(seat1, seat2).collect(Collectors.toList());
        Assert.assertTrue(mySqlSeatDAO.findAll().containsAll(seatList));
    }

    @Test
    public void save() {
        mySqlSeatDAO.delete(seat1);
        Assert.assertEquals(seat1, mySqlSeatDAO.save(seat1).orElse(null));
    }

    @Test
    public void findAllBySessionId() {
        Ticket ticket1 = Ticket.builder().seat(seat1).movieSession(movieSession1).user(user).build();
        Ticket ticket2 = Ticket.builder().seat(seat2).movieSession(movieSession1).user(user).build();
        List<Ticket> tickets = Stream.of(ticket1, ticket2).collect(Collectors.toList());
        List<Ticket> received = mySqlTicketDAO.getTicketsForUserMovie(user, movieSession1.getId()).orElse(Collections.emptyList());
        Assert.assertTrue(received.containsAll(tickets));
    }
}