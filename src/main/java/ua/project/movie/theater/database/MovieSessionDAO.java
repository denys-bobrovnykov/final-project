package ua.project.movie.theater.database;

import ua.project.movie.theater.database.helpers.MySortOrder;
import ua.project.movie.theater.database.helpers.Page;
import ua.project.movie.theater.database.model.MovieSession;

import java.util.List;
import java.util.Optional;

public interface MovieSessionDAO extends GenericCrudDAO<MovieSession> {
    List<MovieSession> findAll();
    Page<MovieSession> findPageSorted(List<MySortOrder> order, Integer page, Integer size);
    Page<MovieSession> findPageSorted(List<MySortOrder> order, Integer page, Integer size, String key, String value);

    Optional<Integer> delete(MovieSession build);
}
