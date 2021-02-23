package ua.project.movie.theater.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.project.movie.theater.database.MovieSessionDAO;
import ua.project.movie.theater.database.helpers.MySortOrder;
import ua.project.movie.theater.database.helpers.Page;
import ua.project.movie.theater.database.model.MovieSession;
import ua.project.movie.theater.exception.AppException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MovieSession service.
 */
public class MovieSessionService {
    private final Logger logger = LogManager.getLogger(MovieSessionService.class);
    private final MovieSessionDAO movieSessionDAO;

    public MovieSessionService(MovieSessionDAO movieSessionDAO) {
        this.movieSessionDAO = movieSessionDAO;
    }

    /**
     * Gets rows for main page.
     * Chooses one of overloaded methods depending on parameters.
     * @param sortParam sort parameters
     * @param sortDir sort direction
     * @param keyword name of column for LIKE filter
     * @param value value
     * @param page page number
     * @return list of MovieSession models
     * @see MovieSession
     */
    public Page<MovieSession> getIndexTableData(List<String> sortParam, String sortDir, String keyword, String value, Integer page) {
        if (keyword != null && !keyword.equals("")) {
            return movieSessionDAO.findPageSorted(getSortOrders(sortParam, getDirection(sortDir)), page, 5, keyword, value);
        }
        return movieSessionDAO
                .findPageSorted(getSortOrders(sortParam, getDirection(sortDir)), page, 5);
    }

    /**
     * Gets sort order direction ASC or DESC.
     * @param sortDir sort direction string
     * @return MySortOrder.Direction
     */
    private MySortOrder.Direction getDirection(String sortDir) {
        return "desc".equals(sortDir) ? MySortOrder.Direction.DESC : MySortOrder.Direction.ASC;
    }

    /**
     * Makes list of orders for SQL string.
     * @param sortParam table column names
     * @param direction sort direction
     * @return List with MySortOrder
     * @see MySortOrder
     */
    private List<MySortOrder> getSortOrders(List<String> sortParam, MySortOrder.Direction direction) {
        return !sortParam.isEmpty() && !"".equals(sortParam.get(0))
                ? sortParam.stream().map(el -> new MySortOrder(el, direction)).collect(Collectors.toList())
                : Arrays.asList(new MySortOrder("ms.day_of_session"), new MySortOrder("ms.time_start"));
    }

    public MovieSession save(MovieSession movieSession) throws AppException {
        logger.info("Trying to save new movie session: {}", movieSession);
        return movieSessionDAO.save(movieSession).orElseThrow(() -> new AppException("Could not create movie"));
    }

    public MovieSession getSessionFromDbById(Integer id) throws AppException {
        return movieSessionDAO.findOne(MovieSession.builder().id(id).build())
                .orElseThrow(() -> new AppException("Could not find movie session"));
    }

    public Integer cancelSession(Integer id) throws AppException {
        logger.info("Deleting movie session with id: {}", id);
        return movieSessionDAO.delete(MovieSession.builder().id(id).build())
                .orElseThrow(() -> new AppException("Could not delete movie session"));
    }
}
