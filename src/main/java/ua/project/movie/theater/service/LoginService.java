package ua.project.movie.theater.service;

import ua.project.movie.theater.database.DAOFactory;
import ua.project.movie.theater.database.UserDAO;
import ua.project.movie.theater.database.model.User;
import ua.project.movie.theater.exception.AppException;

/**
 * Login service
 */
public class LoginService {
    private final UserDAO userDAO;
    public LoginService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User checkUserInDb(User user) throws AppException {
        return userDAO
                .findOne(user).orElseThrow(() -> new AppException("No user in db"));
    }

    public User saveNewUser(User user) throws AppException {
        return userDAO.save(user).orElseThrow(() -> new AppException("User not saved"));
    }
}
