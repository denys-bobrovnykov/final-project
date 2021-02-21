package ua.project.movie.theater.service;

import ua.project.movie.theater.database.MovieDAO;
import ua.project.movie.theater.database.model.Movie;
import ua.project.movie.theater.exception.AppException;

import java.util.List;

public class MovieService {
    private final MovieDAO movieDAO;

    public MovieService(MovieDAO movieDao) {
        this.movieDAO = movieDao;
    }

    public List<Movie> getAllMovies() {
        return movieDAO.findAll();
    }

    public Movie createMovie(Movie movie) throws AppException {
        return movieDAO.save(movie).orElseThrow(() -> new AppException("Could not create movie"));
    }
}
