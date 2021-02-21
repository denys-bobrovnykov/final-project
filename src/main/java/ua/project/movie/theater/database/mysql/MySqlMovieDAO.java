package ua.project.movie.theater.database.mysql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.project.movie.theater.database.MovieDAO;
import ua.project.movie.theater.database.model.Movie;
import ua.project.movie.theater.database.properties.MySqlProperties;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.project.movie.theater.database.connection.ConnectionPool.closeResourcesWithLogger;
import static ua.project.movie.theater.database.helpers.Mappers.mapMovie;

/**
 * MovieDAO implementation
 */
public class MySqlMovieDAO implements MovieDAO {
    private static final String FIND_ALL = MySqlProperties.getValue("find.all.movies");
    private static final String CREATE_MOVIE = MySqlProperties.getValue("create.movie");
    private final Logger logger = LogManager.getLogger(MySqlMovieDAO.class);
    private final DataSource connectionPool;


    public MySqlMovieDAO(DataSource connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Optional<Movie> findOne(Movie movie) {
        return Optional.empty();
    }

    @Override
    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            stmt = connection.prepareStatement(FIND_ALL);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                movies.add(mapMovie(resultSet));
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeResourcesWithLogger(connection, stmt, resultSet, logger);
        }
        return movies;
    }

    @Override
    public Optional<Movie> save(Movie movie) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            stmt = connection.prepareStatement(CREATE_MOVIE, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, movie.getTitleEn());
            stmt.setString(2, movie.getTitleUa());
            stmt.setInt(3, movie.getReleaseYear());
            stmt.setInt(4, movie.getRunningTime());
            stmt.setString(5, movie.getPoster());
            int rowCount = stmt.executeUpdate();
            if (rowCount == 0) {
                return Optional.empty();
            }
            resultSet = stmt.getGeneratedKeys();
            resultSet.next();
            movie.setId(resultSet.getInt(1));
            return Optional.of(movie);
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeResourcesWithLogger(connection, stmt, resultSet, logger);
        }
        return Optional.empty();
    }
}
