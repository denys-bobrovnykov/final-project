package ua.epam.project.movie.theater;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerTest {
    private static final Logger logger = LogManager.getRootLogger();

    public static void main(String[] args) {
        logger.error("Error test");
    }
}
