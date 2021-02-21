package ua.project.movie.theater.commands.login;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.project.movie.theater.commands.Command;
import ua.project.movie.theater.commands.CommandUtility;
import ua.project.movie.theater.database.DAOFactory;
import ua.project.movie.theater.exception.AppException;
import ua.project.movie.theater.service.LoginService;
import ua.project.movie.theater.database.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.project.movie.theater.commands.CommandUtility.getFlashAttributesContainer;

public class LoginCommand implements Command {
    private final Logger logger = LogManager.getRootLogger();
    private final LoginService loginService = new LoginService(DAOFactory.getDAOFactory().getUserDAO());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        if (request.getMethod().equals("POST")) {
            try {
                User user = loginService.checkUserInDb(User.builder()
                        .email(email).password(pass)
                        .build());
                if (passwordEncoder.matches(pass, user.getPassword())) {
                    if (CommandUtility.isLoggedIn(request, user)) {
                        logger.error("User {} already logged in!", user.getEmail());
                        request.setAttribute("error", "error.already.logged.in");
                        return "/login.jsp";
                    }
                    getFlashAttributesContainer(request).put("success", "login");
                    return request.getContextPath() + "redirect:/home";
                }
            } catch (AppException ex) {
                logger.info(ex.getMessage(), ex.getCause());
                request.setAttribute("error", "error.wrong.user");
                return "/login.jsp";
            }
            request.setAttribute("error", "error.wrong.password");
            request.setAttribute("email", email);
        }
        return "/login.jsp";
    }
}
