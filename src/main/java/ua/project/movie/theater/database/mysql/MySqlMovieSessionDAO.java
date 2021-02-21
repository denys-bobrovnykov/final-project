package ua.project.movie.theater.database.mysql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.project.movie.theater.database.MovieSessionDAO;
import ua.project.movie.theater.database.helpers.Page;
import ua.project.movie.theater.database.helpers.SqlQueryBuilder;
import ua.project.movie.theater.database.model.MovieSession;
import ua.project.movie.theater.database.properties.MySqlProperties;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.project.movie.theater.database.connection.ConnectionPool.closeResourcesWithLogger;
import static ua.project.movie.theater.database.helpers.Mappers.mapMovieSession;

public class MySqlMovieSessionDAO implements MovieSessionDAO {
    private static final String COUNT_FILTERED = MySqlProperties.getValue("count.all.sessions.filter");
    private static final String ALIAS = MySqlProperties.getValue("count.alias");
    private final Logger logger = LogManager.getLogger(MySqlMovieSessionDAO.class);
    private final DataSource connectionPool;
    private static final String FIND_ALL = MySqlProperties.getValue("find.all.sessions");
    private static final String COUNT_ALL = MySqlProperties.getValue("count.all.sessions");
    private static final String ORDER_BY = MySqlProperties.getValue("order.by");
    private static final String LIMIT_p1_OFFSET_p2 = MySqlProperties.getValue("limit.offset");

    public MySqlMovieSessionDAO(DataSource connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<MovieSession> findAll() {
        List<MovieSession> movieSessions = new ArrayList<>();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            stmt = connection.prepareStatement(FIND_ALL);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                movieSessions.add(mapMovieSession(resultSet));
            }
            return movieSessions;
        } catch (SQLException ex) {
            logger.error(ex);
        } finally {
            closeResourcesWithLogger(connection, stmt, resultSet, logger);
        }
        return movieSessions;
    }


    @Override
    public Page<MovieSession> findPageSorted(List order, Integer pageNum, Integer pageSize) {
        Page<MovieSession> movieSessionPage = new Page<>();
        int totalRowsCount;
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                resultSet = statement.executeQuery(COUNT_ALL);
                resultSet.next();
                totalRowsCount = resultSet.getInt(1);
            }
            movieSessionPage.setPageCount(pageSize, totalRowsCount);
            stmt = connection
                    .prepareStatement(SqlQueryBuilder
                            .buildQuery(FIND_ALL, ORDER_BY, buildOrdersString(order), LIMIT_p1_OFFSET_p2));
            stmt.setInt(1, pageSize);
            stmt.setInt(2, pageNum * pageSize);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                movieSessionPage.add(mapMovieSession(resultSet));
            }
            connection.commit();
            return movieSessionPage;
        } catch (SQLException ex) {
            logger.error(ex);
        } finally {
            closeResourcesWithLogger(connection, stmt, resultSet, logger);
        }
        return movieSessionPage;
    }

    public Page<MovieSession> findPageSorted(List order, Integer pageNum, Integer pageSize, String keyword, String value) {
        Page<MovieSession> movieSessionPage = new Page<>();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(SqlQueryBuilder.buildQuery(COUNT_FILTERED, "WHERE "+ keyword +" LIKE ?", ALIAS))) {
                statement.setString(1, "%" + value + "%");
                resultSet = statement.executeQuery();
                resultSet.next();
                int totalRowsCount = resultSet.getInt("row_count");
                movieSessionPage.setPageCount(pageSize, totalRowsCount);
            }
            stmt = connection
                    .prepareStatement(SqlQueryBuilder
                            .buildQuery(FIND_ALL, "WHERE "+ keyword +" LIKE ?", ORDER_BY, buildOrdersString(order),
                                    LIMIT_p1_OFFSET_p2));
            stmt.setString(1, "%" + value + "%");
            stmt.setInt(2, pageSize);
            stmt.setInt(3, pageNum * pageSize);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                movieSessionPage.add(mapMovieSession(resultSet));
            }
            connection.commit();
            return movieSessionPage;
        } catch (SQLException ex) {
            logger.error(ex);
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex2) {
                  logger.error(ex2);
                }
                closeResourcesWithLogger(connection, stmt, resultSet, logger);
            }
        } finally {
            closeResourcesWithLogger(connection, stmt, resultSet, logger);
        }
        return movieSessionPage;
    }

    @Override
    public Optional<MovieSession> findOne(MovieSession movieSession) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            stmt = connection.prepareStatement(SqlQueryBuilder.buildQuery(FIND_ALL, "WHERE ms.id = ?"));
            stmt.setInt(1, movieSession.getId());
            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapMovieSession(resultSet));
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeResourcesWithLogger(connection, stmt, resultSet, logger);
        }
        return Optional.empty();
    }

    @Override
    public Optional<MovieSession> save(MovieSession movieSession) {
        return Optional.empty();
    }

    @Override
    public Integer update(MovieSession movieSession) {
        return null;
    }

    private String buildOrdersString(List mySortOrders) {
        StringBuilder builder = new StringBuilder();
        mySortOrders.forEach(el -> builder.append(",").append(el));
        return builder.substring(1);
    }

}
