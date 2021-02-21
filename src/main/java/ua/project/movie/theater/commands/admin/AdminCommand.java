package ua.project.movie.theater.commands.admin;

import ua.project.movie.theater.commands.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        return "/WEB-INF/admin.jsp";
    }
}
