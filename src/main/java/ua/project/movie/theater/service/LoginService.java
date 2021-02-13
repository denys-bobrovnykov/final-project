package ua.epam.project.movie.theater.service;

import ua.epam.project.movie.theater.database.DAOFactory;
import ua.epam.project.movie.theater.database.model.User;
import ua.epam.project.movie.theater.exception.AppException;


public class LoginService {

    public User checkUserInDb(User user) throws AppException {
        return DAOFactory.getDAOFactory()
                .getUserDAO()
                .findOne(user).orElseThrow(() -> new AppException("No user in db"));
    }

    public User saveNewUser(User user) throws AppException {
        return DAOFactory.getDAOFactory().getUserDAO().save(user).orElseThrow(() -> new AppException("User not saved"));
    }
}
