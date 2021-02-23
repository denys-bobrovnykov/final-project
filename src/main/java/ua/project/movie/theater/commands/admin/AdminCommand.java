package ua.project.movie.theater.commands.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.project.movie.theater.commands.Command;
import ua.project.movie.theater.database.DAOFactory;
import ua.project.movie.theater.database.model.User;
import ua.project.movie.theater.service.MovieService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.project.movie.theater.commands.CommandUtility.copyFlash;

public class AdminCommand implements Command {
    private final Logger logger = LogManager.getLogger(AdminCommand.class);
    private final MovieService movieService;

    public AdminCommand() {
        this.movieService = new MovieService(DAOFactory.getDAOFactory().getMovieDao());
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("User {} entered admin section", ((User) request.getSession().getAttribute("user")).getEmail());
        request.setAttribute("flash", copyFlash(request));
        request.setAttribute("movies", movieService.getAllMovies());
        return "/WEB-INF/admin.jsp";
    }
}
