package ua.epam.project.movie.theater.commands;


import ua.epam.project.movie.theater.database.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class CommandUtility {

    public static boolean isLoggedIn(HttpServletRequest request, User user) {
        HashSet<User> loggedUsers = (HashSet<User>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");

        if(loggedUsers.stream().anyMatch(user::equals)){
            return true;
        }
        loggedUsers.add(user);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        return false;
    }

    public static List<String> getSortParamList(String[] sortParam) {
        return sortParam.length > 0 ? Arrays.asList(sortParam) : new ArrayList<>(0);
    }

    public static  String[] getSortParam(HttpServletRequest request) {
        return request.getParameterMap().get("sort") != null ? request.getParameterMap().get("sort") : new String[]{};
    }

    public static  int getPageNumber(HttpServletRequest request) {
        return request.getParameter("page") == null
                || Integer.valueOf(request.getParameter("page")) < 0
                ? 0
                : Integer.valueOf(request.getParameter("page"));
    }

    public static  String getSortParamsForPage(String[] sortParam) {
        StringBuilder builder = new StringBuilder();
        Arrays.stream(sortParam).forEach(param -> builder.append("&sort=").append(param));
        return builder.toString().length() > 0 ?  builder.substring("&sort=".length()) : builder.toString();
    }

}
