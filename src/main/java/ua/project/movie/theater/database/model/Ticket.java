package ua.project.movie.theater.database.model;

public class Ticket {
    private Integer id;
    private MovieSession movieSession;
    private Seat seat;
    private User user;

    public Ticket() {
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

    private Ticket(Builder builder) {
        this.id = builder.id;
        this.movieSession = builder.movieSession;
        this.seat = builder.seat;
        this.user = builder.user;
    }



    public static Builder builder() {
        return new Builder();
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
}
