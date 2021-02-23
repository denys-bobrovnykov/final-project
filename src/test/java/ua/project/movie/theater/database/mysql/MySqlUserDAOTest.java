package ua.project.movie.theater.database.mysql;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.project.movie.theater.TestConnectionPool;
import ua.project.movie.theater.database.model.Movie;
import ua.project.movie.theater.database.model.MovieSession;
import ua.project.movie.theater.database.model.Seat;
import ua.project.movie.theater.database.model.User;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ua.project.movie.theater.service.MockData.*;

public class MySqlUserDAOTest {
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
        Assert.assertEquals(TEST_USER, mySqlUserDAO.findOne(user).orElse(null));
    }
}