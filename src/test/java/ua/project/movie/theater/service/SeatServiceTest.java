package ua.project.movie.theater.service;

import org.junit.Assert;
import org.junit.Test;
import ua.project.movie.theater.database.SeatDAO;
import ua.project.movie.theater.database.model.Seat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static ua.project.movie.theater.service.MockData.TEST_SEAT_1;
import static ua.project.movie.theater.service.MockData.TEST_SEAT_2;

public class SeatServiceTest {
    class MockSeatDAO implements SeatDAO {

        @Override
        public Optional<Seat> findOne(Seat seat) {
            return seat.equals(TEST_SEAT_1) ? Optional.of(TEST_SEAT_1) : Optional.empty();
        }

        @Override
        public List<Seat> findAll() {
            return Stream.of(TEST_SEAT_1, TEST_SEAT_2).collect(Collectors.toList());
        }

        @Override
        public Optional<Seat> save(Seat seat) {
            return seat.equals(TEST_SEAT_1) ? Optional.of(TEST_SEAT_1) : Optional.empty();
        }

        @Override
        public List<Seat> findAllBySessionId(Integer id) {
            return Stream.of(TEST_SEAT_1, TEST_SEAT_2).collect(Collectors.toList());
        }
    }

    private final SeatService seatService = new SeatService(new MockSeatDAO());

    @Test
    public void getsAllSeatsFromDBTest() {
        List<Seat> seats = seatService.getAllSeatsFromDB();
        Stream.of(TEST_SEAT_1, TEST_SEAT_2).forEach(el -> Assert.assertTrue(seats.contains(el)));
    }

    @Test
    public void getsSeatsForSessionTest() {
        List<Seat> seats = seatService.getSeatsForSession(1);
        Stream.of(TEST_SEAT_1, TEST_SEAT_2).forEach(el -> Assert.assertTrue(seats.contains(el)));
    }
}