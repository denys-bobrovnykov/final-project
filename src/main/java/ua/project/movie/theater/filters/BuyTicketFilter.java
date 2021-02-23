package ua.project.movie.theater.filters;



import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.nonNull;

/**
 * Auth filter for admin pages
 */
public class BuyTicketFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
        /*
        Required for Filter initialization
         */
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
                req.getRequestDispatcher(req.getRequestURI().substring(req.getContextPath().length())).forward(req, res);
        } else {
            ((HttpServletResponse) response).sendRedirect(req.getContextPath() + "/app/home");
        }
    }

    @Override
    public void destroy() {
        /*
        Required for Filter destroy
         */
    }
}
