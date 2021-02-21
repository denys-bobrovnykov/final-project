package ua.project.movie.theater.service;

import ua.project.movie.theater.database.model.*;

import java.time.LocalDate;
import java.time.LocalTime;


public class MockData {
    public static final Movie TEST_MOVIE_1 = Movie.builder()
            .id(1).titleEn("Movie 1").titleUa("Фільм 1")
            .releaseYear(2020)
            .runningTime(120).poster("file.jpg").build();
    public static final Movie TEST_MOVIE_2 = Movie.builder()
            .id(1).titleEn("Movie 2").titleUa("Фільм 2")
            .releaseYear(2020)
            .runningTime(120).poster("file.jpg").build();
    public static final User TEST_USER = User.builder()
            .id(1).email("test@email").password("pass")
            .role(User.Role.USER).build();
    public static final User TEST_ADMIN = User.builder()
            .id(2).email("admin@email").password("pass")
            .role(User.Role.ADMIN).build();
    public static final MovieSession TEST_SESSION_1 = MovieSession.builder()
            .id(1).dayOfSession(LocalDate.parse("2020-02-02"))
            .timeStart(LocalTime.parse("10:00:00")).movie(TEST_MOVIE_1)
            .seatsAvailable(10).build();
    public static final MovieSession TEST_SESSION_2 = MovieSession.builder()
            .id(2).dayOfSession(LocalDate.parse("2020-02-02"))
            .timeStart(LocalTime.parse("12:00:00")).movie(TEST_MOVIE_2)
            .seatsAvailable(10).build();
    public static final Seat TEST_SEAT_1 = Seat.builder()
            .id(1).row(1).number(2).build();
    public static final Seat TEST_SEAT_2 = Seat.builder()
            .id(2).row(2).number(2).build();
    public static final Ticket TEST_TICKET_1 = Ticket.builder()
            .id(1).user(TEST_USER)
            .seat(TEST_SEAT_1)
            .movieSession(TEST_SESSION_1).build();
    public static final Ticket TEST_TICKET_2 = Ticket.builder()
            .id(2).user(TEST_USER)
            .seat(TEST_SEAT_2)
            .movieSession(TEST_SESSION_1).build();
}
