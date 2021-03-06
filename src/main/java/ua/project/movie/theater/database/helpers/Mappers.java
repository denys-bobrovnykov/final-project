package ua.project.movie.theater.database.helpers;

import ua.project.movie.theater.database.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Mappers for models.
 * They use predefined fields from resultset
 */
public class Mappers {

    private Mappers() {}

    public static MovieSession mapMovieSession(ResultSet resultSet) throws SQLException {
        return MovieSession.builder()
                .id(resultSet.getInt("ms.id"))
                .dayOfSession(LocalDate.parse(resultSet.getString("ms.day_of_session")))
                .timeStart(LocalTime.parse(resultSet.getString("ms.time_start")))
                .movie(mapMovie(resultSet))
                .seatsAvailable(resultSet.getInt("seats_avail"))
                .build();
    }

    public static Seat mapSeat(ResultSet resultSet) throws SQLException {
        return Seat.builder().id(resultSet.getInt("s.id"))
                .row(resultSet.getInt("s.seat_row"))
                .number(resultSet.getInt("s.seat_number")).build();
    }

    public static User mapUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("id"))
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .role(User.Role.valueOf(resultSet.getString("role")))
                .build();
    }

    public static Movie mapMovie(ResultSet resultSet) throws SQLException {
        return Movie.builder()
                .id(resultSet.getInt("m.id"))
                .titleEn(resultSet.getString("m.title_en"))
                .titleUa(resultSet.getString("m.title_ua"))
                .releaseYear(resultSet.getInt("m.release_year"))
                .runningTime(resultSet.getInt("m.running_time"))
                .poster(resultSet.getString("m.poster"))
                .build();
    }

    public static Ticket mapTicket(ResultSet resultSet) throws SQLException {
        return Ticket.builder()
                .id(resultSet.getInt("t.id"))
                .movieSession(mapMovieSession(resultSet))
                .seat(mapSeat(resultSet))
                .user(User.builder()
                        .id(resultSet.getInt("t.user_id")).build())
                .build();
    }

}
