package ua.epam.project.movie.theater.commands.admin;

import ua.epam.project.movie.theater.commands.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminCommandCreateMovie implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "/WEB-INF/admin.jsp";
    }
}
