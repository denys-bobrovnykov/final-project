package ua.epam.project.movie.theater.database;

import ua.epam.project.movie.theater.database.mysql.MySqlDAOFactory;
import ua.epam.project.movie.theater.database.postgres.PostgresDAOFactory;

public abstract class DAOFactory {
    public static final String POSTGRES = "Postgres";
    public static final String MYSQL = "MySQL";

    public static DAOFactory getDAOFactory() {
        return MySqlDAOFactory.getInstance();
    }

    public static DAOFactory getDAOFactory(String name) {
        if (POSTGRES.equalsIgnoreCase(name)) {
            return PostgresDAOFactory.getInstance();
        } else if (MYSQL.equalsIgnoreCase(name)) {
            return MySqlDAOFactory.getInstance();
        }
        throw new RuntimeException("Unknown factory");
    }

    public abstract UserDAO getUserDAO();
    public abstract MovieSessionDAO getMovieSessionDAO();
}
