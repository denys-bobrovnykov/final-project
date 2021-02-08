package ua.epam.project.movie.theater.controllers;



import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Test connection
 */
@WebServlet("/conn")
public class ConnectionTest extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        try (Connection conn = ((DataSource) getServletContext().getAttribute("dbConnectionPool")).getConnection()){
            writer.println(conn.getCatalog());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            writer.println("Error");
        }
        writer.println("Hello");
    }
}
