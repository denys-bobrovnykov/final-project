package ua.project.movie.theater.service;

import org.junit.Assert;
import org.junit.Test;
import ua.project.movie.theater.database.MovieSessionDAO;
import ua.project.movie.theater.database.helpers.MySortOrder;
import ua.project.movie.theater.database.helpers.Page;
import ua.project.movie.theater.database.model.MovieSession;
import ua.project.movie.theater.exception.AppException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static ua.project.movie.theater.service.MockData.TEST_SESSION_1;
import static ua.project.movie.theater.service.MockData.TEST_SESSION_2;

public class MovieSessionServiceTest {
    class MockMovieSessionDAO implements MovieSessionDAO {

        @Override
        public Optional<MovieSession> findOne(MovieSession movieSession) {
            return movieSession.getId() == TEST_SESSION_1.getId() ? Optional.of(TEST_SESSION_1) : Optional.empty();
        }

        @Override
        public Optional<MovieSession> save(MovieSession movieSession) {
            return movieSession.equals(TEST_SESSION_1) ? Optional.of(TEST_SESSION_1) : Optional.empty();
        }

        @Override
        public List<MovieSession> findAll() {
            return null;
        }

        @Override
        public Page<MovieSession> findPageSorted(List<MySortOrder> order, Integer page, Integer size) {
            return Stream.of(TEST_SESSION_1, TEST_SESSION_2)
                    .collect(Collectors.toCollection(Page::new));
        }

        @Override
        public Page<MovieSession> findPageSorted(List<MySortOrder> order, Integer page, Integer size, String key, String value) {
            return Stream.of(TEST_SESSION_1, TEST_SESSION_2)
                    .collect(Collectors.toCollection(Page::new));
        }

        @Override
        public Optional<Integer> delete(MovieSession movieSession) {
            return movieSession.getId() == TEST_SESSION_1.getId() ? Optional.of(1) : Optional.empty();
        }
    }

    private final MovieSessionService movieSessionService= new MovieSessionService(new MockMovieSessionDAO());

    @Test
    public void getsIndexTableDataNotFiltered() {
        Page<MovieSession> sessions = movieSessionService.getIndexTableData(Collections.EMPTY_LIST, "DESC", null, null, 1);
        Stream.of(TEST_SESSION_1, TEST_SESSION_2).forEach(el -> Assert.assertTrue(sessions.contains(el)));
    }
    @Test
    public void getsIndexTableDataFiltered() {
        Page<MovieSession> sessions = movieSessionService.getIndexTableData(Collections.EMPTY_LIST, "DESC", "keyword", "value", 1);
        Stream.of(TEST_SESSION_1, TEST_SESSION_2).forEach(el -> Assert.assertTrue(sessions.contains(el)));
    }

    @Test
    public void returnsModelOnSuccessSave() throws AppException {
        Assert.assertEquals(TEST_SESSION_1, movieSessionService.save(TEST_SESSION_1));
    }
    @Test(expected = AppException.class)
    public void throwsErrorIfNotSavedMovieSession() throws AppException {
        movieSessionService.save(TEST_SESSION_2);
    }

    @Test
    public void getsSessionFromDbById() throws AppException {
        Assert.assertEquals(TEST_SESSION_1, movieSessionService.getSessionFromDbById(TEST_SESSION_1.getId()));
    }

    @Test(expected = AppException.class)
    public void throwsErrorIfNotFoundMovieSession() throws AppException {
       movieSessionService.getSessionFromDbById(TEST_SESSION_2.getId());
    }

    @Test
    public void returnsRowCount1OnDelete() throws AppException {
        int expected = movieSessionService.cancelSession(TEST_SESSION_1.getId());
        Assert.assertEquals(1, expected);

    }

    @Test(expected = AppException.class)
    public void throwsErrorIfNotDeletedSession() throws AppException {
        movieSessionService.cancelSession(TEST_SESSION_2.getId());
    }

}