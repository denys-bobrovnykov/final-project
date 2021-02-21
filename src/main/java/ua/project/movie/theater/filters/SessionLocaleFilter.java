package ua.project.movie.theater.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SessionLocaleFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        System.out.println("In LOCALE FILTER");
        System.out.println(req.getSession());
        if (req.getParameter("locale") != null && !"".equals(req.getParameter("locale"))) {
            System.out.println(req.getParameter("locale"));
            req.getSession().setAttribute("currentLocale", req.getParameter("locale"));
        }
        System.out.println(req.getSession().getAttribute("currentLocale"));
        chain.doFilter(request, response);
    }
    public void destroy() {}
    public void init(FilterConfig arg0) throws ServletException {}
}