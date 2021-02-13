package ua.epam.project.movie.theater.database.model;

public class Ticket {
    private Integer id;
    private Integer movieSessionId;
    private Integer seatId;
    private Integer userId;

    public Ticket() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMovieSessionId() {
        return movieSessionId;
    }

    public void setMovieSessionId(Integer movieSessionId) {
        this.movieSessionId = movieSessionId;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
