package ua.project.movie.theater.listeners;

import ua.project.movie.theater.database.model.User;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.HashSet;

public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute("flash", new HashMap<String, Object>());
        se.getSession().setAttribute("currentLocale", "ua");
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
        se.getSession().removeAttribute("flash");
    }
}
