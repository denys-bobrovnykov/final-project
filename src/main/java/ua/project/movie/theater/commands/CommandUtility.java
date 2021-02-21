package ua.project.movie.theater.commands;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.project.movie.theater.database.model.User;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class CommandUtility {
    private static final Logger logger = LogManager.getLogger(CommandUtility.class);

    public static boolean isLoggedIn(HttpServletRequest request, User user) {
        HashSet<User> loggedUsers = (HashSet<User>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");

        if(loggedUsers.stream().anyMatch(user::equals)){
            return true;
        }
        loggedUsers.add(user);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        request.getSession().setAttribute("user", user);
        return false;
    }

    public static List<String> getParamList(String[] sortParam) {
        return sortParam.length > 0 ? Arrays.asList(sortParam) : new ArrayList<>(0);
    }

    public static  String[] getParams(HttpServletRequest request, String param) {
        return request.getParameterMap().get(param) != null ? (String[]) request.getParameterMap().get(param) : new String[]{};
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

    public static HashMap<String, Object> getFlashAttributesContainer(HttpServletRequest request) {
        return (HashMap<String, Object>) request.getSession().getAttribute("flash");
    }

    public static HashMap<String, Object> copyFlash(HttpServletRequest request) {
        HashMap<String, Object> original = (HashMap<String, Object>) request.getSession().getAttribute("flash");
        HashMap<String, Object> copy = new HashMap<>();
        copy.putAll(original);
        return copy;
    }

    public static String getValidationProperty(String key) {
        try (InputStream inputStream = CommandUtility.class.getClassLoader().getResourceAsStream("validation.properties")) {
            Properties props = new Properties();
            props.load(inputStream);
            return props.getProperty(key);
        } catch (IOException e) {
            logger.error("Property file not found", e);
        }
        return "";
    }

}
