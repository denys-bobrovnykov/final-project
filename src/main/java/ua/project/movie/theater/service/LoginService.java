package ua.project.movie.theater.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.project.movie.theater.database.UserDAO;
import ua.project.movie.theater.database.model.User;
import ua.project.movie.theater.exception.AppException;


/**
 * Login service
 */
public class LoginService {
    private final Logger logger = LogManager.getLogger(LoginService.class);
    private final UserDAO userDAO;
    public LoginService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User checkUserInDb(User user) throws AppException {
        logger.info("Checking if user {} is logged in already", user.getEmail());
        return userDAO
                .findOne(user).orElseThrow(() -> new AppException("No user in db"));
    }

    public User saveNewUser(User user) throws AppException {
        logger.info("Saving new user: {}", user.getEmail());
        return userDAO.save(user).orElseThrow(() -> new AppException("User not saved"));
    }
}
