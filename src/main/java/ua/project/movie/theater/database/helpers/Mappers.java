package ua.project.movie.theater.database.helpers;

import ua.project.movie.theater.database.model.Movie;
import ua.project.movie.theater.database.model.MovieSession;
import ua.project.movie.theater.database.model.Seat;
import ua.project.movie.theater.database.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Mappers {

    public static MovieSession mapMovieSession(ResultSet resultSet) throws SQLException {
        return MovieSession.builder()
                .id(resultSet.getInt("ms.id"))
                .dayOfSession(LocalDate.parse(resultSet.getString("ms.day_of_session")))
                .timeStart(LocalTime.parse(resultSet.getString("ms.time_start")))
                .movie(Movie.builder()
                        .id(resultSet.getInt("m.id"))
                        .titleEn(resultSet.getString("m.title_en"))
                        .titleUa(resultSet.getString("m.title_ua"))
                        .releaseYear(resultSet.getInt("m.release_year"))
                        .runningTime(resultSet.getInt("m.running_time"))
                        .poster(resultSet.getString("m.poster"))
                        .build())
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

}
