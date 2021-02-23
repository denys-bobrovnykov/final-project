package ua.project.movie.theater.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.project.movie.theater.database.MovieDAO;
import ua.project.movie.theater.database.model.Movie;
import ua.project.movie.theater.exception.AppException;

import java.util.List;

/**
 * Movie service
 */
public class MovieService {
    private final Logger logger = LogManager.getLogger(MovieService.class);
    private final MovieDAO movieDAO;

    public MovieService(MovieDAO movieDao) {
        this.movieDAO = movieDao;
    }

    public List<Movie> getAllMovies() {
        return movieDAO.findAll();
    }

    public Movie createMovie(Movie movie) throws AppException {
        logger.info("Trying to create new movie {}", movie);
        return movieDAO.save(movie).orElseThrow(() -> new AppException("Could not create movie"));
    }
}
