package ua.project.movie.theater.commands.admin;

import ua.project.movie.theater.commands.Command;
import ua.project.movie.theater.database.DAOFactory;
import ua.project.movie.theater.service.MovieService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.project.movie.theater.commands.CommandUtility.copyFlash;

public class AdminCommand implements Command {
    private final MovieService movieService;

    public AdminCommand() {
        this.movieService = new MovieService(DAOFactory.getDAOFactory().getMovieDao());
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("flash", copyFlash(request));
        request.setAttribute("movies", movieService.getAllMovies());
        return "/WEB-INF/admin.jsp";
    }
}
