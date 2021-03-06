package ua.project.movie.theater.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter redirects any requests to home page
 */
public class IndexPageFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        /*
        Must be defined for implementation
         */
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (request.getRequestURI()
                .substring(request.getContextPath().length()).matches("/app[/]?$")
        ) {
            response.sendRedirect(request.getContextPath() + "/app/home");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        /*
        Must be defined for implementation
         */
    }
}
