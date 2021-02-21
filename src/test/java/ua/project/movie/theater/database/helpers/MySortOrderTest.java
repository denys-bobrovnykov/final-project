package ua.project.movie.theater.database.helpers;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MySortOrderTest {

    @Test
    public void returnsCorrectDefaultOrder() {
        String expected = "test";
        Assert.assertEquals(expected,new MySortOrder("test").toString());
    }
    @Test
    public void returnsCorrectDESCOrder() {
        String expected = "test DESC";
        Assert.assertEquals(expected,new MySortOrder("test", MySortOrder.Direction.DESC).toString());
    }
}