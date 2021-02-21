package ua.project.movie.theater.commands.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.project.movie.theater.commands.Command;
import ua.project.movie.theater.database.DAOFactory;
import ua.project.movie.theater.database.model.Movie;
import ua.project.movie.theater.exception.AppException;
import ua.project.movie.theater.service.MovieService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static ua.project.movie.theater.commands.CommandUtility.getFlashAttributesContainer;
import static ua.project.movie.theater.commands.CommandUtility.getValidationProperty;

public class AdminCommandMovie implements Command {
    private static final String POSTER_REGEX = getValidationProperty("file");
    private final Logger logger = LogManager.getLogger(AdminCommandMovie.class);
    private final MovieService movieService;

    public AdminCommandMovie() {
        movieService = new MovieService(DAOFactory.getDAOFactory().getMovieDao());
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String titleEn = request.getParameter("title_en");
        String titleUa = request.getParameter("title_ua");
        String releaseYear = request.getParameter("release_year");
        String runningTime = request.getParameter("running_time");
        String poster = request.getParameter("poster");
        List<String> errors = validateMovieFields(titleEn, titleUa, releaseYear, runningTime, poster);
        if (!errors.isEmpty()) {
            getFlashAttributesContainer(request).put("errors", errors);
            return request.getContextPath() + "redirect:/admin";
        }
        try {
            movieService.createMovie(Movie.builder().titleEn(titleEn)
            .titleUa(titleUa)
            .runningTime(Integer.parseInt(runningTime))
            .releaseYear(Integer.parseInt(releaseYear))
            .poster(poster).build());
        } catch (AppException ex) {
            logger.error(ex);
            getFlashAttributesContainer(request).put("failed", "movie");
            return request.getContextPath() + "redirect:/admin";
        }
        getFlashAttributesContainer(request).put("success", "movie");
        return request.getContextPath() + "redirect:/admin";
    }

    private List<String> validateMovieFields(String titleEn, String titleUa, String releaseYear, String runningTime, String poster) {
        List<String> messages = new ArrayList<>();
        if (titleEn == null || "".equals(titleEn)) {
            messages.add("error.empty.title.en");
        }
        if (titleUa == null || "".equals(titleUa)) {
            messages.add("error.empty.title.ua");
        }
        try {
            Integer.parseInt(releaseYear);
        } catch (NumberFormatException ex) {
            logger.error("Invalid release year", ex);
            messages.add("error.empty.release.year");
        }
        try {
            Integer.parseInt(runningTime);
        } catch (NumberFormatException ex) {
            logger.error("Invalid movie running time", ex);
            messages.add("error.empty.running.time");
        }
        if (!poster.matches(POSTER_REGEX)) {
            logger.error("Poster file invalid");
            messages.add("error.field.valid.file.poster");
        }
        return messages;
    }
}
