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
import static ua.project.movie.theater.database.helpers.Mappers.mapMovie;
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
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            stmt = connection.prepareStatement("SELECT s.* FROM seat s WHERE s.seat_row = ? AND s.seat_number = ?");
            stmt.setInt(1, seat.getRow());
            stmt.setInt(2, seat.getNumber());
            resultSet = stmt.executeQuery();
            resultSet.next();
            return Optional.of(mapSeat(resultSet));
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeResourcesWithLogger(connection, stmt, resultSet, logger);
        }
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
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            stmt = connection.prepareStatement("INSERT INTO seat (seat_row, seat_number) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, seat.getRow());
            stmt.setInt(2, seat.getNumber());
            stmt.executeUpdate();
            resultSet = stmt.getGeneratedKeys();
            if (resultSet.next()) {
                seat.setId(resultSet.getInt(1));
                return Optional.of(seat);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeResourcesWithLogger(connection, stmt, resultSet, logger);
        }
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

    public Optional<Integer> delete(Seat seat1) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement("DELETE FROM seat WHERE seat_row = ? AND seat_number = ?");
            statement.setInt(1, seat1.getRow());
            statement.setInt(2, seat1.getNumber());
            int rowCount = statement.executeUpdate();
            return Optional.of(rowCount);

        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeResourcesWithLogger(connection, statement, null, logger);
        }
        return Optional.empty();
    }
}
