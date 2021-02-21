package ua.project.movie.theater.service;

import ua.project.movie.theater.database.DAOFactory;
import ua.project.movie.theater.database.model.User;
import ua.project.movie.theater.exception.AppException;


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
