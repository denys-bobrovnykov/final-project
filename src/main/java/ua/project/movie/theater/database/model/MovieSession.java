package ua.project.movie.theater.database.model;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class MovieSession {
    private Integer id;
    private LocalDate dayOfSession;
    private LocalTime timeStart;
    private Movie movie;
    private List<Seat> seats;
    private Integer seatsAvailable;

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDayOfSession() {
        return dayOfSession;
    }

    public void setDayOfSession(LocalDate dayOfSession) {
        this.dayOfSession = dayOfSession;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public Integer getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(Integer seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public static Builder builder() {
        return new Builder();
    }

    private MovieSession(Builder builder) {
        this.id = builder.id;
        this.dayOfSession = builder.dayOfSession;
        this.timeStart = builder.timeStart;
        this.movie = builder.movie;
        this.seatsAvailable = builder.seatsAvailable;
        this.seats = builder.seats;
    }

    public static class Builder {
         Integer id;
         LocalDate dayOfSession;
         LocalTime timeStart;
         Movie movie;
         List<Seat> seats;
         Integer seatsAvailable;

        public MovieSession build() {
            return new MovieSession(this);
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }
        public Builder dayOfSession(LocalDate dayOfSession) {
            this.dayOfSession = dayOfSession;
            return this;
        }
        public Builder timeStart(LocalTime timeStart) {
            this.timeStart = timeStart;
            return this;
        }
        public Builder seatsAvailable(Integer seatsAvailable) {
            this.seatsAvailable = seatsAvailable;
            return this;
        }

        public Builder movie(Movie movie) {
            this.movie = movie;
            return this;
        }
        public Builder seats(List<Seat> movie) {
            this.seats = seats;
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieSession that = (MovieSession) o;

        if (!Objects.equals(dayOfSession, that.dayOfSession)) return false;
        return Objects.equals(timeStart, that.timeStart);
    }

    @Override
    public int hashCode() {
        int result = dayOfSession != null ? dayOfSession.hashCode() : 0;
        result = 31 * result + (timeStart != null ? timeStart.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MovieSession{" +
                "dayOfSession=" + dayOfSession +
                ", timeStart=" + timeStart +
                ", movie=" + movie +
                ", seats=" + seatsAvailable +
                '}';
    }
}
