package ua.project.movie.theater.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.project.movie.theater.database.SeatDAO;
import ua.project.movie.theater.database.model.Seat;
import ua.project.movie.theater.database.model.User;
import ua.project.movie.theater.exception.AppException;

import java.util.List;

public class SeatService {
    private final Logger logger = LogManager.getLogger(SeatService.class);
    private SeatDAO seatDAO;

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
