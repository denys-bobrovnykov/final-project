package ua.project.movie.theater.database;

import ua.project.movie.theater.database.model.Seat;

import java.util.List;
import java.util.Optional;

public interface SeatDAO extends GenericCrudDAO<Seat>{
    List<Seat> findAllBySessionId(Integer id);
}
