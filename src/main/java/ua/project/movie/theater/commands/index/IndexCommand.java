package ua.epam.project.movie.theater.commands.index;

import ua.epam.project.movie.theater.commands.Command;
import ua.epam.project.movie.theater.database.helpers.Page;
import ua.epam.project.movie.theater.database.model.MovieSession;
import ua.epam.project.movie.theater.service.MovieSessionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ua.epam.project.movie.theater.commands.CommandUtility.*;

public class IndexCommand implements Command {

    private MovieSessionService movieSessionService = new MovieSessionService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Integer page = getPageNumber(request);
        String[] sortParam = getSortParam(request);
        String sortDir = request.getParameter("sort_dir");
        String keyword = request.getParameter("search");
        String value = request.getParameter("value");
        List<String> sortParamList = getSortParamList(sortParam);
        Page<MovieSession> movieSessionPage = movieSessionService.getIndexTableData(sortParamList, sortDir, keyword, value, page);
        request.setAttribute("rows", movieSessionPage);
        request.setAttribute("current_page", page);
        request.setAttribute("pages", movieSessionPage.getPageCount());
        request.setAttribute("sortParam", getSortParamsForPage(sortParam));
        request.setAttribute("sortDir", sortDir);
        request.setAttribute("revSortDir", "asc".equals(sortDir) ? "desc" : "asc");
        request.setAttribute("search", keyword == null
                ? "" : keyword);
        request.setAttribute("value", value == null ? "" : value);
        return "/index.jsp";
    }

}
