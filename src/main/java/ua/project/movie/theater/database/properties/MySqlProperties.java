package ua.epam.project.movie.theater.database.properties;

import ua.epam.project.movie.theater.database.connection.ConnectionPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MySqlProperties {
    private static final String FILE_NAME = "db.properties";

    public static String getValue(String key) {
        try (InputStream inputStream = ConnectionPool.class.getClassLoader().getResourceAsStream(FILE_NAME)) {
            Properties props = new Properties();
            props.load(inputStream);
            return key != null ? props.getProperty(key) : "";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
