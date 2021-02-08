package ua.epam.project.movie.theater.controllers;

import ua.epam.project.movie.theater.database.DAOFactory;
import ua.epam.project.movie.theater.database.entity.User;


public class LoginService {

    public User checkUserInDb(User user) throws Exception {
        return DAOFactory.getDAOFactory()
                .getUserDAO()
                .findOne(user).orElseThrow(() -> new Exception("No user in db"));
    }
}
