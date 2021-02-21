package ua.project.movie.theater.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Session locale filter.
 * Keeps information about selected locale in session
 */
public class SessionLocaleFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getParameter("locale") != null && !"".equals(req.getParameter("locale"))) {
            req.getSession().setAttribute("currentLocale", req.getParameter("locale"));
        }
        chain.doFilter(request, response);
    }
    public void destroy() {
        /*
        Must bu defined for implementation
         */
    }
    public void init(FilterConfig arg0) throws ServletException {
        /*
        Must bu defined for implementation
         */
    }
}