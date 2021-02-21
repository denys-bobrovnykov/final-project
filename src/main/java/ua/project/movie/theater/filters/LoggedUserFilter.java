package ua.project.movie.theater.filters;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.nonNull;

/**
 * Filter blocks registration and login routes for already logged in user.
 */
public class LoggedUserFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
         /*
        Must be defined for implementation
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
            if (req.getRequestURI()
                    .substring(req.getContextPath().length()).matches("/app/login[/]?$")
                    || req.getRequestURI()
                    .substring(req.getContextPath().length()).matches("/app/registration[/]?$")
            ) {
                res.sendRedirect(req.getContextPath() + "/app/home");
            } else {
                filterChain.doFilter(request, response);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
         /*
        Must be defined for implementation
         */
    }
}
