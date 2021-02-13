package ua.epam.project.movie.theater.database.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.project.movie.theater.database.properties.MySqlProperties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static ConnectionPool connectionPool;
    private static DataSource ds;

    private ConnectionPool() {
        logger.info("Creating connection pool");
    }

//    public Connection getConnection() {
//        Context ctx;
//        Connection connection = null;
//        try {
//            Properties props = new Properties();
//            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("db.properties");
//            props.load(inputStream);
//            ctx = new InitialContext();
//            DataSource ds = (DataSource) ctx.lookup(props.getProperty("pool.resource.name"));
//            connection = ds.getConnection();
//            logger.info("Connection created");
//        } catch (NamingException | SQLException | FileNotFoundException ex) {
//            logger.error("Connection has not been created", ex);
//        } catch (IOException e) {
//            logger.error("Property file not found", e);
//        }
//        return connection;
//    }

    public static synchronized DataSource getConnectionPool() {
        if (ds == null) {
            Context ctx;
            try {
                ctx = new InitialContext();
                ds = (DataSource) ctx.lookup(MySqlProperties.getValue("pool.resource.name"));
//                ds = (DataSource) ctx.lookup(props.getProperty("pool.resource.name"));
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return ds;
    }

//    public static synchronized ConnectionPool getInstance() {
//        if (connectionPool == null) {
//            connectionPool = new ConnectionPool();
//            logger.info("Connection pool created");
//        }
//        return connectionPool;
//    }
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
