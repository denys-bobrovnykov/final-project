package ua.project.movie.theater.service;

import org.junit.Assert;
import org.junit.Test;
import ua.project.movie.theater.database.MovieDAO;
import ua.project.movie.theater.database.model.Movie;
import ua.project.movie.theater.exception.AppException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static ua.project.movie.theater.service.MockData.TEST_MOVIE_1;
import static ua.project.movie.theater.service.MockData.TEST_MOVIE_2;

public class MovieServiceTest {
    class MockMovieDAO implements MovieDAO {

        @Override
        public Optional<Movie> findOne(Movie movie) {
            return Optional.empty();
        }

        @Override
        public List<Movie> findAll() {
            return Stream.of(TEST_MOVIE_1, TEST_MOVIE_2).collect(Collectors.toList());
        }

        @Override
        public Optional<Movie> save(Movie movie) {
            return movie.equals(TEST_MOVIE_1) ? Optional.of(movie) : Optional.empty();
        }
    }

    private final MovieService movieService = new MovieService(new MockMovieDAO());

    @Test
    public void getsAllMoviesTest() {
        List<Movie> received = movieService.getAllMovies();
        Stream.of(TEST_MOVIE_1, TEST_MOVIE_2)
                .forEach(el -> Assert.assertTrue(received.contains(el)));;
    }

    @Test
    public void returnsMovieAfterCreation() throws AppException {
        Assert.assertEquals(TEST_MOVIE_1, movieService.createMovie(TEST_MOVIE_1));
    }
    @Test(expected = AppException.class)
    public void throwsErrorIfMovieNotCreated() throws AppException {
       movieService.createMovie(TEST_MOVIE_2);
    }
}