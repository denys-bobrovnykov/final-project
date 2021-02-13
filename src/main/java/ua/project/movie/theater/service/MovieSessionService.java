package ua.epam.project.movie.theater.service;

import ua.epam.project.movie.theater.database.DAOFactory;
import ua.epam.project.movie.theater.database.MovieSessionDAO;
import ua.epam.project.movie.theater.database.helpers.MySortOrder;
import ua.epam.project.movie.theater.database.helpers.Page;
import ua.epam.project.movie.theater.database.model.MovieSession;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MovieSessionService {

    public Page<MovieSession> getIndexTableData(List<String> sortParam, String sortDir, String keyword, String value, Integer page) {
        MovieSessionDAO movieSessionDAO = DAOFactory
                .getDAOFactory()
                .getMovieSessionDAO();
        if (keyword != null && !keyword.equals("")) {
            return movieSessionDAO.findPageSorted(getSortOrders(sortParam, getDirection(sortDir)), page, 5, keyword, value);
        }
        return DAOFactory
                .getDAOFactory()
                .getMovieSessionDAO()
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
}
