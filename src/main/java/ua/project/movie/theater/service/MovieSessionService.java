package ua.project.movie.theater.service;

import ua.project.movie.theater.database.MovieSessionDAO;
import ua.project.movie.theater.database.helpers.MySortOrder;
import ua.project.movie.theater.database.helpers.Page;
import ua.project.movie.theater.database.model.MovieSession;
import ua.project.movie.theater.exception.AppException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MovieSessionService {
    private MovieSessionDAO movieSessionDAO;

    public MovieSessionService(MovieSessionDAO movieSessionDAO) {
        this.movieSessionDAO = movieSessionDAO;
    }

    public Page<MovieSession> getIndexTableData(List<String> sortParam, String sortDir, String keyword, String value, Integer page) {
        if (keyword != null && !keyword.equals("")) {
            return movieSessionDAO.findPageSorted(getSortOrders(sortParam, getDirection(sortDir)), page, 5, keyword, value);
        }
        return movieSessionDAO
                .findPageSorted(getSortOrders(sortParam, getDirection(sortDir)), page, 5);
    }

    private MySortOrder.Direction getDirection(String sortDir) {
        return "desc".equals(sortDir) ? MySortOrder.Direction.DESC : MySortOrder.Direction.ASC;
    }

    private List<String> getSortOrders(List<String> sortParam, MySortOrder.Direction direction) {
        return sortParam.size() > 0 && !"".equals(sortParam.get(0))
                ? sortParam.stream().map(el -> new MySortOrder(el, direction).toString()).collect(Collectors.toList())
                : Arrays.asList(new MySortOrder("ms.day_of_session").toString(), new MySortOrder("ms.time_start").toString());
    }

    public MovieSession save(MovieSession build) {
        return null;
    }

    public MovieSession getSessionFromDbById(Integer id) throws AppException {
        return movieSessionDAO.findOne(MovieSession.builder().id(id).build())
                .orElseThrow(() -> new AppException("Could not find movie session"));
    }
}
