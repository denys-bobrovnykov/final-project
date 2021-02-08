package ua.epam.project.movie.theater.database.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlConnectionPool {
    private static final Logger logger = LogManager.getLogger(MySqlConnectionPool.class);
    private static MySqlConnectionPool mysqlConnectionPool;

    private MySqlConnectionPool() {
        logger.info("Creating connection pool");
    }

    public Connection getConnection() {
        Context ctx;
        Connection connection = null;
        try {
            Properties props = new Properties();
            props.load(new FileReader("db.properties"));
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/mysqlConnPool/mydb");
            connection = ds.getConnection();
            logger.info("Connection created");
        } catch (NamingException | SQLException | FileNotFoundException ex) {
            logger.error("Connection has not been created", ex);
        } catch (IOException e) {
            logger.error("Property file not found", e);
        }
        return connection;
    }

    public static synchronized MySqlConnectionPool getInstance() {
        if (mysqlConnectionPool == null) {
            mysqlConnectionPool = new MySqlConnectionPool();
            logger.info("Connection pool created");
        }
        return mysqlConnectionPool;
    }
}
