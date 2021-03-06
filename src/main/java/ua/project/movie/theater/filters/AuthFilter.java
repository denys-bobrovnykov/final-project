package ua.project.movie.theater.filters;


import ua.project.movie.theater.database.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.nonNull;

/**
 * Auth filter for admin pages
 */
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain)
            throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;
        final HttpSession session = req.getSession();

        if (nonNull(session) &&
                nonNull(session.getAttribute("user"))) {
            User.Role role = ((User) session.getAttribute("user")).getRole();
            if ("ADMIN".equalsIgnoreCase(String.valueOf(role))) {
                req.getRequestDispatcher(req.getRequestURI().substring(req.getContextPath().length())).forward(req, res);
            } else {
                ((HttpServletResponse) response).sendRedirect(req.getContextPath() + "/app/home");
            }
        } else {
            ((HttpServletResponse) response).sendRedirect(req.getContextPath() + "/app/home");
        }
    }

    @Override
    public void destroy() {

    }
}
