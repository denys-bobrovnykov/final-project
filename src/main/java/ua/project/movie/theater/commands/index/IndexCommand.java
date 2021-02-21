package ua.project.movie.theater.commands.index;

import ua.project.movie.theater.commands.Command;
import ua.project.movie.theater.database.DAOFactory;
import ua.project.movie.theater.database.helpers.Page;
import ua.project.movie.theater.database.model.MovieSession;
import ua.project.movie.theater.service.MovieSessionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static ua.project.movie.theater.commands.CommandUtility.*;

public class IndexCommand implements Command {

    private MovieSessionService movieSessionService = new MovieSessionService(DAOFactory.getDAOFactory().getMovieSessionDAO());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Integer page = getPageNumber(request);
        String[] sortParam = getParams(request, "sort");
        String sortDir = request.getParameter("sort_dir");
        String keyword = request.getParameter("search");
        String value = request.getParameter("value");
        List<String> sortParamList = getParamList(sortParam);
        Page<MovieSession> movieSessionPage = movieSessionService.getIndexTableData(sortParamList, sortDir, keyword, value, page);
        request.setAttribute("flash", copyFlash(request));
//        getFlashAttributesContainer(request).clear();
        request.setAttribute("rows", movieSessionPage);
        request.setAttribute("current_page", page);
        request.setAttribute("pages", movieSessionPage.getPageCount());
        request.setAttribute("sortParam", getSortParamsForPage(sortParam));
        request.setAttribute("sortDir", sortDir);
        request.setAttribute("revSortDir", "desc".equals(sortDir) ? "asc" : "desc");
        request.setAttribute("search", keyword == null
                ? "" : keyword);
        request.setAttribute("value", value == null ? "" : value);
        return "/index.jsp";
    }

}
