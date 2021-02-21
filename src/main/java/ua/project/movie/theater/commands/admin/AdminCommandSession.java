package ua.project.movie.theater.commands.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.project.movie.theater.commands.Command;
import ua.project.movie.theater.database.DAOFactory;
import ua.project.movie.theater.database.model.Movie;
import ua.project.movie.theater.database.model.MovieSession;
import ua.project.movie.theater.service.MovieSessionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static ua.project.movie.theater.commands.CommandUtility.getFlashAttributesContainer;

/**
 * This command reacts on POST request to /app/admin/sessions.
 * On GET redirects to /app/admin
 * It handles mew movie session creation.
 */
public class AdminCommandSession implements Command {
    private final Logger logger = LogManager.getLogger(AdminCommandSession.class);
    private MovieSessionService movieSessionService;

    public AdminCommandSession() {
        movieSessionService = new MovieSessionService(DAOFactory.getDAOFactory().getMovieSessionDAO());
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            String day = request.getParameter("day_of_session");
            String time = request.getParameter("time_start");
            String movieId = request.getParameter("movie_id");
            try {
                List<String> errors = getValidationErrors(day, time, movieId);
                getFlashAttributesContainer(request).put("errors", errors);
                if (errors.size() > 0) {
                    return request.getContextPath() + "redirect:/admin";
                }
                movieSessionService.save(MovieSession.builder()
                .dayOfSession(LocalDate.parse(day))
                .timeStart(LocalTime.parse(time))
                .movie(Movie.builder()
                        .id(Integer.parseInt(movieId))
                        .build())
                .build());
                getFlashAttributesContainer(request).put("success", "movieSession");
                return request.getRequestURI() + "redirect:/app/admin";
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return request.getContextPath() + "redirect:/admin";
    }

    private List<String> getValidationErrors(String day, String time, String movieId) {
        List<String> validationErrors = new ArrayList<>();
        try {
            LocalDate.parse(day);
        } catch (Exception ex) {
            validationErrors.add("must.be.present.date");
        }
        try {
            LocalTime.parse(time);
        } catch (Exception ex) {
            validationErrors.add("must.be.present.time");
        }
       try {
           Integer.parseInt(movieId);
       } catch (NumberFormatException ex) {
           validationErrors.add("movie.id.not.number");
       }
        return validationErrors;
    }

}
