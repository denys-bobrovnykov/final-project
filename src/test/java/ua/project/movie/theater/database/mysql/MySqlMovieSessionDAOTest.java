package ua.project.movie.theater.database.mysql;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.project.movie.theater.TestConnectionPool;
import ua.project.movie.theater.database.helpers.MySortOrder;
import ua.project.movie.theater.database.model.Movie;
import ua.project.movie.theater.database.model.MovieSession;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ua.project.movie.theater.service.MockData.*;

public class MySqlMovieSessionDAOTest {

    private static final MySqlMovieSessionDAO mySqlMovieSessionDAO = new MySqlMovieSessionDAO(TestConnectionPool.getInstance());
    private static final MySqlMovieDAO mySqlMovieDAO = new MySqlMovieDAO(TestConnectionPool.getInstance());
    private static MovieSession movieSession1;
    private static MovieSession movieSession2;
    private static Movie movie1;
    private static Movie movie2;

    @BeforeClass
    public static void populateDatabase() {
        movie1 = mySqlMovieDAO.save(TEST_MOVIE_1).orElse(null);
        movie2 = mySqlMovieDAO.save(TEST_MOVIE_2).orElse(null);
        movieSession1 = mySqlMovieSessionDAO.save(MovieSession.builder()
                .dayOfSession(TEST_SESSION_1.getDayOfSession())
                .timeStart(TEST_SESSION_1.getTimeStart()).movie(movie1).build()).orElse(null);
        movieSession2 = mySqlMovieSessionDAO.save(MovieSession.builder()
                .dayOfSession(TEST_SESSION_2.getDayOfSession())
                .timeStart(TEST_SESSION_2.getTimeStart()).movie(movie2).build()).orElse(null);
    }
    @AfterClass
    public static void clearDatabase() {
        mySqlMovieSessionDAO.delete(movieSession1);
        mySqlMovieSessionDAO.delete(movieSession2);
        mySqlMovieDAO.delete(movie1);
        mySqlMovieDAO.delete(movie2);
    }

    @Test
    public void findAll() {
        mySqlMovieSessionDAO.save(movieSession2);
        mySqlMovieSessionDAO.save(movieSession1);
        List<MovieSession> movieSessionList = Stream.of(movieSession1, movieSession2).collect(Collectors.toList());
        Assert.assertTrue(mySqlMovieSessionDAO.findAll().containsAll(movieSessionList));
    }

    @Test
    public void findPageSorted() {
        List<MySortOrder> orders = Arrays.asList(new MySortOrder("ms.day_of_session"), new MySortOrder("ms.time_start"));
        Assert.assertFalse(mySqlMovieSessionDAO.findPageSorted(orders, 0, 1).isEmpty());
    }

    @Test
    public void testFindPageSorted() {
        List<MySortOrder> orders = Arrays.asList(new MySortOrder("ms.day_of_session"), new MySortOrder("ms.time_start"));
       Assert.assertFalse(mySqlMovieSessionDAO.findPageSorted(orders, 0, 1, "m.title_en", "movie").isEmpty());
    }

    @Test
    public void delete() {
        Assert.assertEquals(java.util.Optional.of(1).get(), mySqlMovieSessionDAO.delete(movieSession1).orElse(null));
    }

    @Test
    public void findOne() {
        Assert.assertEquals(movieSession2, mySqlMovieSessionDAO.findOne(movieSession2).orElse(null));
    }

    @Test
    public void save() {
        mySqlMovieSessionDAO.delete(movieSession1);
        movieSession1 = mySqlMovieSessionDAO.save(MovieSession.builder()
                .dayOfSession(TEST_SESSION_1.getDayOfSession())
                .timeStart(TEST_SESSION_1.getTimeStart()).movie(movie1).build()).orElse(null);
        Assert.assertEquals(TEST_SESSION_1, movieSession1);
    }
}