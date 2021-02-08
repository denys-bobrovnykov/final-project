package ua.epam.project.movie.theater.dao;

import ua.epam.project.movie.theater.dao.mysql.MySqlDAOFactory;

public abstract class DAOFactory {
    public MySqlDAOFactory getDAOFactory() {
        return MySqlDAOFactory.getInstance();
    }
    public abstract UserDAO getUserDAO();

}
