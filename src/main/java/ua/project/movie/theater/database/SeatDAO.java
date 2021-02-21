package ua.project.movie.theater.database;

import ua.project.movie.theater.database.model.Seat;

import java.util.List;

/**
 * SeatDAO interface.
 * Extends GenericCrudDAO
 * @see GenericCrudDAO
 */
public interface SeatDAO extends GenericCrudDAO<Seat>{
    List<Seat> findAllBySessionId(Integer id);
}
