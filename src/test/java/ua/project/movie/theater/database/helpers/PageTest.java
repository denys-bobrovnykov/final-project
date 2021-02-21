package ua.project.movie.theater.database.helpers;

import org.junit.Assert;
import org.junit.Test;


public class PageTest {
    private final Page<Object> testPage = new Page<>();
    @Test
    public void getsPageCountCountsCorrectly() {
        int expected = 3;
        testPage.setPageCount(3, 8);
        int actual = testPage.getPageCount();
        Assert.assertEquals(expected, actual);
    }
}