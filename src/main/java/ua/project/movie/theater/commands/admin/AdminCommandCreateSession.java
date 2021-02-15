package ua.project.movie.theater.commands.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.project.movie.theater.commands.Command;
import ua.project.movie.theater.database.model.Movie;
import ua.project.movie.theater.database.model.MovieSession;
import ua.project.movie.theater.exception.AppException;
import ua.project.movie.theater.service.MovieSessionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AdminCommandCreateMovie implements Command {
    private final Logger logger = LogManager.getLogger(AdminCommandCreateMovie.class);
    private MovieSessionService movieSessionService;

    public AdminCommandCreateMovie() {
        movieSessionService = MovieSessionService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            List<String> errors = new ArrayList<>();
            LocalDate dayOfSession = LocalDate.parse(request.getParameter("day_of_session"));
            LocalTime timeStart = LocalTime.parse(request.getParameter("time_start"));
            Integer movieId = Integer.parseInt(request.getParameter("movie_id"));
            try {
                movieSessionService.save(MovieSession.builder()
                .dayOfSession(dayOfSession)
                .timeStart(timeStart)
                .movie(Movie.builder()
                        .id(movieId)
                        .build())
                .build());
            } catch (AppException e) {
                logger.error(e);
            }
        }
        return "";
    }
}
