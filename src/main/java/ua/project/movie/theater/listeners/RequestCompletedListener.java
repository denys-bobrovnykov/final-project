package ua.project.movie.theater.listeners;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class RequestCompletedListener implements ServletRequestListener {
    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest request = (HttpServletRequest) servletRequestEvent.getServletRequest();

        if (request.getAttribute("flash") != null) {
            ((HashMap<String, Object>)request.getSession().getAttribute("flash")).clear();
        }
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {

    }
}
