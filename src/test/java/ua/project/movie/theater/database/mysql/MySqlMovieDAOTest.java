package ua.project.movie.theater.database.mysql;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.project.movie.theater.TestConnectionPool;
import ua.project.movie.theater.database.model.Movie;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ua.project.movie.theater.service.MockData.TEST_MOVIE_1;
import static ua.project.movie.theater.service.MockData.TEST_MOVIE_2;

public class MySqlMovieDAOTest {

    private final static MySqlMovieDAO testMovieDao = new MySqlMovieDAO(TestConnectionPool.getInstance());
    @BeforeClass
    public static void initMovie() {
        testMovieDao.delete(TEST_MOVIE_1);
        testMovieDao.delete(TEST_MOVIE_2);
        testMovieDao.save(TEST_MOVIE_1);
        testMovieDao.save(TEST_MOVIE_2);
    }

    @AfterClass
    public static void deleteMovie() {
        testMovieDao.delete(TEST_MOVIE_1);
        testMovieDao.delete(TEST_MOVIE_2);
    }

    @Test
    public void findOne() {
        Assert.assertEquals(TEST_MOVIE_1, testMovieDao.findOne(TEST_MOVIE_1).orElse(null));
    }

    @Test
    public void findAll() {
        List<Movie> movies = Stream.of(TEST_MOVIE_1, TEST_MOVIE_2).collect(Collectors.toList());
        Assert.assertTrue(testMovieDao.findAll().containsAll(movies));
    }

    @Test
    public void save() {
        Assert.assertEquals(1, (int) testMovieDao.delete(TEST_MOVIE_1).orElse(0));
    }
}