package ua.project.movie.theater;

import org.apache.commons.dbcp.BasicDataSource;
import ua.project.movie.theater.database.properties.MySqlProperties;

public class TestConnectionPool {
    private static BasicDataSource ds;

    public static BasicDataSource getInstance() {
        if (ds == null) {
            ds = new BasicDataSource();
            try {
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            ds.setUrl(MySqlProperties.getValue("test.db.url"));
            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);
        }
        return ds;
    }
}
