package ua.epam.project.movie.theater.database;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GenericCrudDAO<T> {

    Optional<T> findOne(T t);
    List<T> findAll();
    Optional<T> save(T t);
    Integer update(T t);
}
