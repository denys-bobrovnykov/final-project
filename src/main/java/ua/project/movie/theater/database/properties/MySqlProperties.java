package ua.project.movie.theater.database.properties;

import ua.project.movie.theater.database.connection.ConnectionPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Util class.
 * Gets properties for SQL queries from db.properties file
 */
public class MySqlProperties {
    private static final String FILE_NAME = "db.properties";

    public static String getValue(String key) {
        try (InputStream inputStream = ConnectionPool.class.getClassLoader().getResourceAsStream(FILE_NAME)) {
            Properties props = new Properties();
            props.load(inputStream);
            return key != null
                    ? props.getProperty(key) : null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
