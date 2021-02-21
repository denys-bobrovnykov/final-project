package ua.project.movie.theater.commands.logout;

import ua.project.movie.theater.commands.Command;
import ua.project.movie.theater.database.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        HashSet<User> loggedUsers = (HashSet<User>) session.getServletContext().getAttribute("loggedUsers");
        loggedUsers.remove(session.getAttribute("user"));
        session.removeAttribute("user");
        return request.getContextPath() + "redirect:/home";
    }
}
