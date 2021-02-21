package ua.project.movie.theater.database;

import java.util.List;
import java.util.Optional;

/**
 * Generic CRUD DAO interface
 * @param <T> model type
 */
public interface GenericCrudDAO<T> {

    Optional<T> findOne(T t);
    List<T> findAll();
    Optional<T> save(T t);
}
