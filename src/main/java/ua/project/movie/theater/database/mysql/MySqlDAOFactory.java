package ua.project.movie.theater.database.mysql;

import ua.project.movie.theater.database.*;
import ua.project.movie.theater.database.connection.ConnectionPool;

import javax.sql.DataSource;

public class MySqlDAOFactory extends DAOFactory {
    private static MySqlDAOFactory instance;
    private DataSource ds = ConnectionPool.getConnectionPool();

    private MySqlDAOFactory() {
        super();
    }

    public synchronized static MySqlDAOFactory getInstance() {
        if (instance == null) {
            instance = new MySqlDAOFactory();
        }
        return instance;
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
