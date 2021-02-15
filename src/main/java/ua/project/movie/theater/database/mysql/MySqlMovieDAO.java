package ua.project.movie.theater.database.mysql;

import ua.project.movie.theater.database.MovieDAO;

import javax.sql.DataSource;

public class MySqlMovieDAO implements MovieDAO {
    public MySqlMovieDAO(DataSource ds) {
    }
}
