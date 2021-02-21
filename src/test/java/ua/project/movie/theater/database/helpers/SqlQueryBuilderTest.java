package ua.project.movie.theater.database.helpers;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SqlQueryBuilderTest {

    @Test
    public void buildsQueryCorrectly() {
        String expected = "ONE TWO THREE";
        String actual = SqlQueryBuilder.buildQuery("ONE", "TWO", "THREE");
        Assert.assertEquals(expected, actual);
    }
}