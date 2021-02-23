package ua.project.movie.theater.commands.details;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.project.movie.theater.commands.Command;
import ua.project.movie.theater.database.DAOFactory;
import ua.project.movie.theater.exception.AppException;
import ua.project.movie.theater.service.MovieSessionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionDetailsCommand implements Command {
    private final Logger logger = LogManager.getLogger(SessionDetailsCommand.class);
    private final MovieSessionService movieSessionService = new MovieSessionService(DAOFactory.getDAOFactory().getMovieSessionDAO());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("User entered details page");
        Integer id = request.getParameter("id") != null ? Integer.valueOf(request.getParameter("id")) : 0;
        try {
            request.setAttribute("selectedSession", movieSessionService.getSessionFromDbById(id));
        } catch (AppException ex) {
            logger.error("Did not receive movie session from DB", ex);
            request.setAttribute("error", ex);
            return "error";
        }
        return "/sessionDetails.jsp";
    }
}
