package ua.epam.project.movie.theater.database.mysql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import ua.epam.project.movie.theater.database.GenericCrudDAO;
import ua.epam.project.movie.theater.database.UserDAO;
import ua.epam.project.movie.theater.database.model.User;
import ua.epam.project.movie.theater.database.properties.MySqlProperties;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

import static ua.epam.project.movie.theater.database.connection.ConnectionPool.closeResourcesWithLogger;


public class MySqlUserDAO implements GenericCrudDAO<User>, UserDAO {
    private final Logger logger = LogManager.getLogger(MySqlUserDAO.class);
    private final DataSource connectionPool;
    private static final String FIND_USER_BY_EMAIL = MySqlProperties.getValue("find.user.by.email");
    private static final String SAVE_USER = MySqlProperties.getValue("save.user");

    public MySqlUserDAO(DataSource connectionPool) {
        this.connectionPool = connectionPool;
    }


    public Optional<User> findOne(User user) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            stmt = connection.prepareStatement(FIND_USER_BY_EMAIL);
            stmt.setString(1, user.getEmail());
            resultSet = stmt.executeQuery();
            logger.info("User found");
            if (resultSet.next()) {
                logger.info("In resultSet");
                return Optional.of(mapUser(resultSet));
            }
        } catch (SQLException ex) {
            logger.error(ex);
        } finally {
            closeResourcesWithLogger(connection, stmt, resultSet, logger);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> save(User user) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            stmt = connection.prepareStatement(SAVE_USER, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            if (stmt.executeUpdate() == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            resultSet = stmt.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
            connection.commit();
            return Optional.of(user);
        } catch (SQLException ex) {
            logger.error(ex);
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } finally {
            closeResourcesWithLogger(connection, stmt, resultSet, logger);        }
        return Optional.empty();
    }

    @Override
    public Integer update(User user) {
        throw new NotImplementedException();
    }

    private User mapUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("id"))
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .role(User.Role.valueOf(resultSet.getString("role")))
                .build();
    }

}