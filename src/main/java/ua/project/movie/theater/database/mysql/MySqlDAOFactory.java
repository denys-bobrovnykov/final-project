package ua.project.movie.theater.database.mysql;

import ua.project.movie.theater.database.*;
import ua.project.movie.theater.database.connection.ConnectionPool;

import javax.sql.DataSource;

/**
 * DAO factory implementation
 */
public class MySqlDAOFactory extends DAOFactory {
    private final DataSource ds = ConnectionPool.getConnectionPool();

    private MySqlDAOFactory() {
        super();
    }

    public static MySqlDAOFactory getInstance() {
        return new MySqlDAOFactory();
    }

    @Override
    public UserDAO getUserDAO() {
        return new MySqlUserDAO(ds);
    }

    @Override
    public MovieSessionDAO getMovieSessionDAO() {
        return new MySqlMovieSessionDAO(ds);
    }

    @Override
    public MovieDAO getMovieDao() {
        return new MySqlMovieDAO(ds);
    }

    @Override
    public SeatDAO getSeatDAO() {
        return new MySqlSeatDAO(ds);
    }

    @Override
    public TicketDAO getTicketDAO() {
        return new MySqlTicketDAO(ds);
    }
}
