package ua.epam.project.movie.theater.database;

import ua.epam.project.movie.theater.database.helpers.Page;
import ua.epam.project.movie.theater.database.model.MovieSession;

import java.util.List;

public interface MovieSessionDAO extends GenericCrudDAO<MovieSession> {
    List<MovieSession> findAll();
    Page<MovieSession> findPage(Integer page, Integer size);
    Page<MovieSession> findPageSorted(List order, Integer page, Integer size);
    Page<MovieSession> findPageSorted(List order, Integer page, Integer size, String key, String value);

}
