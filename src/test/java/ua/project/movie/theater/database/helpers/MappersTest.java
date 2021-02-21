package ua.project.movie.theater.database.helpers;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import ua.project.movie.theater.database.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;


public class MappersTest {

    private Movie testMovie = Movie.builder()
            .id(1).titleEn("Movie 1").titleUa("Фільм 1")
            .releaseYear(2020)
            .runningTime(120).poster("file.jpg").build();
    private User testUser = User.builder()
            .id(1).email("test@email").password("pass")
            .role(User.Role.USER).build();
    private MovieSession testMovieSession = MovieSession.builder()
            .id(1).dayOfSession(LocalDate.parse("2020-02-02"))
            .timeStart(LocalTime.parse("10:00:00")).movie(testMovie)
            .seatsAvailable(10).build();
    private Seat testSeat = Seat.builder()
            .id(1).row(1).number(2).build();
    private Ticket testTicket = Ticket.builder()
            .id(1).user(testUser)
            .seat(testSeat)
            .movieSession(testMovieSession).build();
    private ResultSet resultSet = Mockito.mock(ResultSet.class);

    @Test
    public void mapMovieSessionTest() throws SQLException {
        Mockito.when(resultSet.getInt("ms.id")).thenReturn(1);
        Mockito.when(resultSet.getString("ms.day_of_session")).thenReturn("2020-02-02");
        Mockito.when(resultSet.getString("ms.time_start")).thenReturn("10:00:00");
        Mockito.when(resultSet.getString("")).thenReturn("10:00:00");
        Mockito.when(resultSet.getInt("seats_available")).thenReturn(10);
        Assert.assertEquals(testMovieSession, Mappers.mapMovieSession(resultSet));
    }

    @Test
    public void mapSeatTest() throws SQLException {
        Mockito.when(resultSet.getInt("s.id")).thenReturn(1);
        Mockito.when(resultSet.getInt("s.seat_row")).thenReturn(1);
        Mockito.when(resultSet.getInt("s.seat_number")).thenReturn(2);
        Assert.assertEquals(testSeat, Mappers.mapSeat(resultSet));
    }

    @Test
    public void mapUserTest() throws SQLException {
        Mockito.when(resultSet.getInt("id")).thenReturn(1);
        Mockito.when(resultSet.getString("email")).thenReturn("test@email");
        Mockito.when(resultSet.getString("password")).thenReturn("pass");
        Mockito.when(resultSet.getString("role")).thenReturn("USER");
        Assert.assertEquals(testUser, Mappers.mapUser(resultSet));
    }

    @Test
    public void mapMovieTest() throws SQLException {
        Mockito.when(resultSet.getInt("m.id")).thenReturn(1);
        Mockito.when(resultSet.getString("m.title_en")).thenReturn("Movie 1");
        Mockito.when(resultSet.getString("m.title_ua")).thenReturn("Фільм 1");
        Mockito.when(resultSet.getInt("m.release_year")).thenReturn(2020);
        Mockito.when(resultSet.getInt("m.running_time")).thenReturn(120);
        Mockito.when(resultSet.getString("m.poster")).thenReturn("file.jpg");
        Assert.assertEquals(testMovie, Mappers.mapMovie(resultSet));
    }
}