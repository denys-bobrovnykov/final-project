package ua.project.movie.theater.database.model;

import java.util.Objects;

/**
 * Ticket model
 */
public class Ticket {
    private Integer id;
    private MovieSession movieSession;
    private Seat seat;
    private User user;

    public Ticket() {
    }

    private Ticket(Builder builder) {
        this.id = builder.id;
        this.movieSession = builder.movieSession;
        this.seat = builder.seat;
        this.user = builder.user;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MovieSession getMovieSession() {
        return movieSession;
    }

    public void setMovieSession(MovieSession movieSession) {
        this.movieSession = movieSession;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class Builder {
        Integer id;
        MovieSession movieSession;
        Seat seat;
        User user;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder movieSession(MovieSession movieSession) {
            this.movieSession = movieSession;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder seat(Seat seat) {
            this.seat = seat;
            return this;
        }

        public Ticket build() {
            return new Ticket(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (!Objects.equals(movieSession, ticket.movieSession))
            return false;
        return Objects.equals(seat, ticket.seat);
    }

    @Override
    public int hashCode() {
        int result = movieSession != null ? movieSession.hashCode() : 0;
        result = 31 * result + (seat != null ? seat.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "movieSession=" + movieSession.getMovie().getTitleEn() +
                ", id=" + id +
                '}';
    }
}
