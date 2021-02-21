package ua.project.movie.theater.database.helpers;

import java.util.Arrays;

/**
 * Util class concatenates SQL queries
 */
public class SqlQueryBuilder {

    public static String buildQuery(String... parts) {
        StringBuilder builder = new StringBuilder();
        Arrays.stream(parts).forEach(part -> builder.append(part).append(" "));
        return builder.toString().trim();
    }

}
