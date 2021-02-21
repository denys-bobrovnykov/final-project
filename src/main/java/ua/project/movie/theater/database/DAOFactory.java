package ua.project.movie.theater.database;

import ua.project.movie.theater.database.mysql.MySqlDAOFactory;

public abstract class DAOFactory {
    public static final String POSTGRES = "Postgres";
    public static final String MYSQL = "MySQL";

    public static DAOFactory getDAOFactory() {
        return MySqlDAOFactory.getInstance();
    }

    public abstract UserDAO getUserDAO();
    public abstract MovieSessionDAO getMovieSessionDAO();

    public abstract MovieDAO getMovieDao();

    public abstract SeatDAO getSeatDAO();

    public abstract TicketDAO getTicketDAO();
}
