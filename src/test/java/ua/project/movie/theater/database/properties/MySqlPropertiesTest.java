package ua.project.movie.theater.database.properties;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MySqlPropertiesTest {

    @Test
    public void getsTestValueFromFile() {
        String expected = "test";
        String actual = MySqlProperties.getValue("test");
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void getsNullStringIfNotFound() {
        String actual = MySqlProperties.getValue("unknown");
        Assert.assertEquals(null, actual);
    }
    @Test
    public void getsNullStringIfNotKeyProvidedOrEmptyStringProvided() {
        String actual = MySqlProperties.getValue("");
        String actual2 = MySqlProperties.getValue(null);
        Assert.assertEquals(null, actual);
        Assert.assertEquals(null, actual2);
    }
 }