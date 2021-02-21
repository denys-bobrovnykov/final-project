package ua.project.movie.theater.service;

import org.junit.Assert;
import org.junit.Test;
import ua.project.movie.theater.database.UserDAO;
import ua.project.movie.theater.database.model.User;
import ua.project.movie.theater.exception.AppException;

import java.util.List;
import java.util.Optional;

import static ua.project.movie.theater.service.MockData.TEST_ADMIN;
import static ua.project.movie.theater.service.MockData.TEST_USER;

public class LoginServiceTest {
    class MockedUserDAO implements UserDAO {

        @Override
        public Optional<User> findOne(User user) {
            return user.equals(TEST_USER) ? Optional.of(TEST_USER) : Optional.empty();
        }

        @Override
        public List<User> findAll() {
            return null;
        }

        @Override
        public Optional<User> save(User user) {
            return user.equals(TEST_USER) ? Optional.of(TEST_USER) : Optional.empty();
        }
    }

    private final LoginService loginService = new LoginService(new MockedUserDAO());

    @Test
    public void checkUserInDbGetsUserIfExists() throws AppException {
        Assert.assertEquals(TEST_USER, loginService.checkUserInDb(TEST_USER));
    }

    @Test(expected = AppException.class)
    public void throwsExceptionIfUserNotFound() throws AppException {
         loginService.checkUserInDb(TEST_ADMIN);
    }

    @Test
    public void savesNewUserAndReturnesThisUserIfOk() throws AppException {
        Assert.assertEquals(TEST_USER, loginService.saveNewUser(TEST_USER));
    }

    @Test(expected = AppException.class)
    public void throwsExceptionIfUserNotSaved() throws AppException {
        loginService.saveNewUser(TEST_ADMIN);
    }

}