package ua.project.movie.theater.listeners;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;

import static org.junit.Assert.*;

public class RequestCompletedListenerTest {
    public final ServletRequestEvent se = Mockito.mock(ServletRequestEvent.class);
    public final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    public final HttpSession session = Mockito.mock(HttpSession.class);
    public final ServletContext context = Mockito.mock(ServletContext.class);

    public HashMap<String, Object> flashMap = new HashMap<>();
    public ServletRequestListener testListener = new RequestCompletedListener();

    @Before
    public void initMocks() {
        flashMap.put("test", "test");
        Mockito.when(se.getServletRequest()).thenReturn(request);
        Mockito.when(request.getAttribute("flash")).thenReturn(flashMap);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getAttribute("flash")).thenReturn(flashMap);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("flash")).thenReturn(flashMap);
    }
    @After
    public void clearContainer() {
        flashMap.clear();
    }
    @Test
    public void requestDestroyedClearsFlash() {
        testListener.requestDestroyed(se);
        Assert.assertTrue(flashMap.size() == 0);
    }
}