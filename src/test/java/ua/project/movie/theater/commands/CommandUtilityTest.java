package ua.project.movie.theater.commands;

import com.sun.deploy.association.utility.MacOSXAppAssociationReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import ua.project.movie.theater.database.model.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.spi.http.HttpContext;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static ua.project.movie.theater.service.MockData.TEST_ADMIN;
import static ua.project.movie.theater.service.MockData.TEST_USER;

public class CommandUtilityTest {

    public final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    public final HttpSession session = Mockito.mock(HttpSession.class);
    public final ServletContext context = Mockito.mock(ServletContext.class);
    private HashSet<User> users = new HashSet<>();

    @Before
    public void mockRequest() {
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getServletContext()).thenReturn(context);
    }

    @Test
    public void isLoggedInReturnsTrueIfLoggedIn() {
        users.add(TEST_USER);
        Mockito.when(context.getAttribute("loggedUsers")).thenReturn(users);
        Assert.assertTrue(CommandUtility.isLoggedIn(request, TEST_USER));
    }

    @Test
    public void isLoggedInReturnsFalseIfNotLoggedIn() {
        users.add(TEST_ADMIN);
        Mockito.when(context.getAttribute("loggedUsers")).thenReturn(users);
        Assert.assertFalse(CommandUtility.isLoggedIn(request, TEST_USER));
    }

    @Test
    public void getParamListTest() {
        List<String> expected = Stream.of("value1", "value2").collect(Collectors.toList());
        List<String> actual = CommandUtility.getParamList(new String[]{"value1", "value2"});
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getParamsTest() {
        Map<String, String[]> paramsMap = new HashMap<>();
        paramsMap.put("test", new String[]{"value1", "value2"});
        Mockito.when(request.getParameterMap()).thenReturn(paramsMap);
        String[] expected = new String[]{"value1", "value2"};
        String[] actual = CommandUtility.getParams(request, "test");
        Assert.assertEquals(expected[0], actual[0]);
        Assert.assertEquals(expected[1], actual[1]);
    }

    @Test
    public void getPageNumberGetsOkPath() {
        Mockito.when(request.getParameter("page")).thenReturn("1");
        Assert.assertEquals(1, CommandUtility.getPageNumber(request));
    }

    @Test
    public void getPageNumberGetsSadPath() {
        Mockito.when(request.getParameter("page")).thenReturn("-1");
        Assert.assertEquals(0, CommandUtility.getPageNumber(request));
    }

    @Test
    public void getSortParamsForPageTest() {
        String expected = "param1&sort=param2";
        String actual = CommandUtility.getSortParamsForPage(new String[]{"param1", "param2"});
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getFlashAttributesContainer() {
        Map<String, Object> expected = new HashMap<>();
        expected.put("test", "test");
        Mockito.when(session.getAttribute("flash")).thenReturn(expected);
        Map<String, Object> actual = CommandUtility.getFlashAttributesContainer(request);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void copyFlashTest() {
        Map<String, Object> expected = new HashMap<>();
        Mockito.when(session.getAttribute("flash")).thenReturn(expected);
        expected.put("test", "test");
        Map<String, Object> actual = CommandUtility.copyFlash(request);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getValidationProperty() {
        Assert.assertEquals("test", CommandUtility.getValidationProperty("test"));
    }
}