package ua.project.movie.theater.commands.registration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.project.movie.theater.commands.Command;
import ua.project.movie.theater.database.DAOFactory;
import ua.project.movie.theater.database.model.User;
import ua.project.movie.theater.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static ua.project.movie.theater.commands.CommandUtility.getFlashAttributesContainer;
import static ua.project.movie.theater.commands.CommandUtility.getValidationProperty;

public class RegistrationCommand implements Command {
    private static final String PASSWORD_REGEX = getValidationProperty("password");
    private static final String EMAIL_REGEX = getValidationProperty("email");
    private final Logger logger = LogManager.getLogger(RegistrationCommand.class);
    private final LoginService loginService = new LoginService(DAOFactory.getDAOFactory().getUserDAO());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            List<String> errors = new ArrayList<>();
            String email = request.getParameter("email");
            String pass = request.getParameter("password");
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
            if (!pass.matches(PASSWORD_REGEX)) {
                errors.add("error.register.password");
            }
            if (!email.matches(EMAIL_REGEX)) {
                errors.add("error.register.email");
            }
            if (email.matches(EMAIL_REGEX) && pass.matches(PASSWORD_REGEX)) {
                logger.info("Encoding password");
                pass = passwordEncoder.encode(pass);
                try {
                    loginService.saveNewUser(User.builder().email(email).password(pass).role(User.Role.USER).build());
                    getFlashAttributesContainer(request).put("success", "registration");
                    return request.getContextPath() + "redirect:/home";
                } catch (Exception ex) {
                    logger.info(ex.getMessage(), ex.getCause());
                    errors.add("creation error");
                    request.setAttribute("errors", errors);
                    return "/registration.jsp";
                }
            }
            logger.warn("Wrong registration data: {}", errors);
            request.setAttribute("errors", errors);
            return "/registration.jsp";
        }
        return "/registration.jsp";
    }
}
