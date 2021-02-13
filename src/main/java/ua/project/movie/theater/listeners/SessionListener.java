package ua.epam.project.movie.theater.listeners;

import ua.epam.project.movie.theater.database.model.User;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;

public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HashSet<User> loggedUsers = (HashSet<User>) se
                .getSession().getServletContext()
                .getAttribute("loggedUsers");
        User user = (User) se.getSession()
                .getAttribute("user");
        loggedUsers.remove(user);
        se.getSession().setAttribute("loggedUsers", loggedUsers);
    }
}
