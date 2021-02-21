package ua.project.movie.theater.commands.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.project.movie.theater.commands.Command;
import ua.project.movie.theater.database.DAOFactory;
import ua.project.movie.theater.exception.AppException;
import ua.project.movie.theater.service.MovieSessionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import static ua.project.movie.theater.commands.CommandUtility.getFlashAttributesContainer;

public class AdminCommandSessionRemove implements Command {
    private final Logger logger = LogManager.getLogger(AdminCommandSessionRemove.class);
    private final MovieSessionService movieSessionService;

    public AdminCommandSessionRemove() {
        this.movieSessionService = new MovieSessionService(DAOFactory.getDAOFactory().getMovieSessionDAO());
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String movieSessionId = request.getParameter("id");
        Integer realId;
        try {
            realId = Integer.parseInt(movieSessionId);
        } catch (NumberFormatException ex) {
            logger.error("Invalid movie session id");
            return request.getContextPath() + "redirect:/home";
        }
        try {
            movieSessionService.cancelSession(realId);
        } catch (AppException e) {
            logger.error("Session not removed");
            getFlashAttributesContainer(request).put("error", "Session not removed");
            return request.getContextPath() + "redirect:/home";
        }
        getFlashAttributesContainer(request).put("success", "removed");
        return request.getContextPath() + "redirect:/home";
    }

}
