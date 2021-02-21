package ua.project.movie.theater.database.mysql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.project.movie.theater.database.TicketDAO;
import ua.project.movie.theater.database.helpers.SqlQueryBuilder;
import ua.project.movie.theater.database.model.Ticket;
import ua.project.movie.theater.database.model.User;
import ua.project.movie.theater.database.properties.MySqlProperties;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.project.movie.theater.database.connection.ConnectionPool.closeResourcesWithLogger;
import static ua.project.movie.theater.database.helpers.Mappers.*;

public class MySqlTicketDAO implements TicketDAO {
    private final Logger logger = LogManager.getLogger(MySqlTicketDAO.class);
    private final DataSource connectionPool;
    private static final String INSERT_INTO_TICKETS = MySqlProperties.getValue("insert.ticket");
    private static final String INSERT_INTO_SESSION_TICKETS = MySqlProperties.getValue("insert.in.session.seat");
    private static final String GET_USER_TICKETS = MySqlProperties.getValue("get.user.tickets");
    private static final String FOR_SESSION = MySqlProperties.getValue("for.buy.page");


    public MySqlTicketDAO(DataSource connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Optional<Integer> buyTickets(Integer movieSessionId, String[] seatIds, int userId) {
        Connection connection = null;
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            stmt = connection.prepareStatement(INSERT_INTO_TICKETS, Statement.RETURN_GENERATED_KEYS);
            for (String seatId : seatIds) {
                stmt.setInt(1, movieSessionId);
                stmt.setInt(2, Integer.parseInt(seatId));
                stmt.setInt(3, userId);
                stmt.addBatch();
            }
            int[] count = stmt.executeBatch();
            resultSet = stmt.getGeneratedKeys();
            stmt2 = connection.prepareStatement(INSERT_INTO_SESSION_TICKETS);
            for (String seatId : seatIds) {
                stmt2.setInt(1, Integer.parseInt(seatId));
                stmt2.setInt(2, movieSessionId);
                stmt2.addBatch();
            }
            int[] count2 = stmt2.executeBatch();
            if (count.length == 0 || count2.length != count.length) {
                logger.error("error while writing to database");
                return Optional.empty();
            }
            connection.commit();
            return Optional.of(count.length);
        } catch (SQLException e) {
            logger.error(e);
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    logger.error(ex);
                }
            }
        } finally {
            closeResourcesWithLogger(connection, stmt, resultSet, logger);
            closeResourcesWithLogger(connection, stmt2, resultSet, logger);
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Ticket>> getTicketsForUserMovie(User user, Integer id) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        List<Ticket> tickets = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();
            stmt = connection.prepareStatement(SqlQueryBuilder.buildQuery(GET_USER_TICKETS, FOR_SESSION));
            stmt.setInt(1, user.getId());
            stmt.setInt(2, id);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                tickets.add(mapTicket(resultSet));
            }
            return Optional.of(tickets);
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeResourcesWithLogger(connection, stmt, resultSet, logger);

        }
        return Optional.empty();
    }

    private Ticket mapTicket(ResultSet resultSet) throws SQLException {
        return Ticket.builder()
                .id(resultSet.getInt("t.id"))
                .movieSession(mapMovieSession(resultSet))
                .seat(mapSeat(resultSet))
                .user(User.builder()
                        .id(resultSet.getInt("t.user_id")).build())
                .build();
    }

}
