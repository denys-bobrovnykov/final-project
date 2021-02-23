package ua.project.movie.theater.listeners;

import org.junit.Test;
import org.mockito.Mockito;
import ua.project.movie.theater.database.model.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.*;

public class SessionListenerTest {
    public final HttpSessionEvent sessionEvent = Mockito.mock(HttpSessionEvent.class);
    public final ServletRequestEvent se = Mockito.mock(ServletRequestEvent.class);
    public final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    public final HttpSession session = Mockito.mock(HttpSession.class);
    public final ServletContext context = Mockito.mock(ServletContext.class);

    public HashMap<String, Object> flashMap = new HashMap<>();
    public HashSet<User> loggedUsers = new HashSet<>();

    private final HttpSessionListener sessionListener = new SessionListener();

    @Test
    public void sessionCreatedTest() {
        Mockito.when(sessionEvent.getSession()).thenReturn(session);
        Mockito.when(sessionEvent.getSession()).thenReturn(session);
        sessionListener.sessionCreated(sessionEvent);
        session.setAttribute("flash", "flash");
        System.out.println(session.getAttribute("flash"));
        System.out.println(session.getAttribute("currentLocale"));
    }

    @Test
    public void sessionDestroyedTest() {
    }
}