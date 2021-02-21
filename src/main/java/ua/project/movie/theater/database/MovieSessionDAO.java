package ua.project.movie.theater.database;

import ua.project.movie.theater.database.helpers.MySortOrder;
import ua.project.movie.theater.database.helpers.Page;
import ua.project.movie.theater.database.model.MovieSession;

import java.util.List;
import java.util.Optional;

/**
 * MovieSessionDAO interface extends GenericCrudDAO
 * @see GenericCrudDAO
 */
public interface MovieSessionDAO extends GenericCrudDAO<MovieSession> {
    List<MovieSession> findAll();

    /**
     * Overloaded method to get data for main page table
     * @param order sort orders
     * @param page page number
     * @param size page size
     * @return list of MovieSession models or empty list
     */
    Page<MovieSession> findPageSorted(List<MySortOrder> order, Integer page, Integer size);

    /**
     * Same but uses LIKE filter
     * @param order sort order
     * @param page page number
     * @param size page size
     * @param key column name
     * @param value value
     * @return list of found MovieSession models or empty list
     */
    Page<MovieSession> findPageSorted(List<MySortOrder> order, Integer page, Integer size, String key, String value);

    Optional<Integer> delete(MovieSession build);
}
