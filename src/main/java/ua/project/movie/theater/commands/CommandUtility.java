package ua.project.movie.theater.commands;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.project.movie.theater.database.model.User;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Command utility class.
 * Contains util methods used in commands.
 */
public class CommandUtility {
    private static final Logger logger = LogManager.getLogger(CommandUtility.class);

    private CommandUtility() {
    }

    public static boolean isLoggedIn(HttpServletRequest request, User user) {
        HashSet<User> loggedUsers = (HashSet<User>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");

        if (loggedUsers.stream().anyMatch(user::equals)) {
            return true;
        }
        loggedUsers.add(user);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        request.getSession().setAttribute("user", user);
        return false;
    }

    public static List<String> getParamList(String[] sortParam) {
        return sortParam.length > 0 ? Arrays.asList(sortParam) : new ArrayList<>();
    }

    public static String[] getParams(HttpServletRequest request, String param) {
        return request.getParameterMap().get(param) != null ? (String[]) request.getParameterMap().get(param) : new String[]{};
    }

    public static int getPageNumber(HttpServletRequest request) {
        return request.getParameter("page") == null
                || Integer.parseInt(request.getParameter("page")) < 0
                ? 0
                : Integer.parseInt(request.getParameter("page"));
    }

    /**
     * Concatenates "&sort=" and additional "&sort=" params into http query for multi sort filter
     * Input ["param1", "param2"]
     * @param sortParam list of sort params
     * @return "param1&sort=param2"
     */
    public static String getSortParamsForPage(String[] sortParam) {
        StringBuilder builder = new StringBuilder();
        Arrays.stream(sortParam).forEach(param -> builder.append("&sort=").append(param));
        return builder.toString().length() > 0 ? builder.substring("&sort=".length()) : builder.toString();
    }

    public static Map<String, Object> getFlashAttributesContainer(HttpServletRequest request) {
        return (HashMap<String, Object>) request.getSession().getAttribute("flash");
    }

    public static Map<String, Object> copyFlash(HttpServletRequest request) {
        HashMap<String, Object> original = (HashMap<String, Object>) request.getSession().getAttribute("flash");
        return new HashMap<>(original);
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
