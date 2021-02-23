package ua.project.movie.theater.database.connection;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.project.movie.theater.database.properties.MySqlProperties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Connection pool creation class.
 */
public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static BasicDataSource ds;

    private ConnectionPool() {
        logger.info("Creating connection pool");
    }

    public static synchronized BasicDataSource getConnectionPool() {
        if (ds == null) {
            ds = new BasicDataSource();
            try {
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            ds.setUrl(MySqlProperties.getValue("prod.db.url"));
            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);
        }
        return ds;
    }

    /**
     * Util for closing all resources
     * @param connection connection
     * @param stmt prepared statement
     * @param resultSet resultset
     * @param logger logger of client
     */
    public static void closeResourcesWithLogger(Connection connection, PreparedStatement stmt, ResultSet resultSet, Logger logger) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
            }
        }
    }

}
