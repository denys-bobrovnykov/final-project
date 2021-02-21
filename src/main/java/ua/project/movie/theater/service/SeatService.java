package ua.project.movie.theater.service;

import ua.project.movie.theater.database.SeatDAO;
import ua.project.movie.theater.database.model.Seat;

import java.util.List;

/**
 * Seat service
 */
public class SeatService {
    private final SeatDAO seatDAO;

    public SeatService(SeatDAO seatDAO) {
        this.seatDAO = seatDAO;
    }

    public List<Seat> getAllSeatsFromDB() {
        return seatDAO.findAll();
    }

    public List<Seat> getSeatsForSession(Integer id) {
        return seatDAO.findAllBySessionId(id);
    }
}
