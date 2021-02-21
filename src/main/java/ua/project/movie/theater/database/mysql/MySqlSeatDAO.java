package ua.project.movie.theater.database.mysql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.project.movie.theater.database.SeatDAO;
import ua.project.movie.theater.database.model.Seat;
import ua.project.movie.theater.database.properties.MySqlProperties;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.project.movie.theater.database.connection.ConnectionPool.closeResourcesWithLogger;
import static ua.project.movie.theater.database.helpers.Mappers.mapSeat;

/**
 * SeatDAO implementation
 */
public class MySqlSeatDAO implements SeatDAO {
    private static final String FIND_ALL_SEATS = MySqlProperties.getValue("find.all.seats");
    private static final String FIND_BY_SESSION_ID = MySqlProperties.getValue("find.seats.session");
    private final Logger logger = LogManager.getLogger(MySqlSeatDAO.class);
    private final DataSource connectionPool;

    public MySqlSeatDAO(DataSource connectionPool) {
        this.connectionPool = connectionPool;
    }


    @Override
    public Optional<Seat> findOne(Seat seat) {
        return Optional.empty();
    }

    @Override
    public List<Seat> findAll() {
        List<Seat> seats = new ArrayList<>();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            stmt = connection.prepareStatement(FIND_ALL_SEATS);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                seats.add(mapSeat(resultSet));
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeResourcesWithLogger(connection, stmt, resultSet, logger);
        }
        return seats;
    }

    @Override
    public Optional<Seat> save(Seat seat) {
        return Optional.empty();
    }

    @Override
    public List<Seat> findAllBySessionId(Integer id) {
        List<Seat> seats = new ArrayList<>();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            stmt = connection.prepareStatement(FIND_BY_SESSION_ID);
            stmt.setInt(1, id);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                seats.add(mapSeat(resultSet));
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeResourcesWithLogger(connection, stmt, resultSet, logger);
        }
        return seats;
    }
}
