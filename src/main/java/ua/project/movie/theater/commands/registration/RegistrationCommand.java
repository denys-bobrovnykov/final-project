package ua.project.movie.theater.commands.registration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.project.movie.theater.commands.Command;
import ua.project.movie.theater.database.model.User;
import ua.project.movie.theater.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static ua.project.movie.theater.commands.CommandUtility.getFlashAttributesContainer;

public class RegistrationCommand implements Command {
    private final Logger logger = LogManager.getLogger(RegistrationCommand.class);
    private final LoginService loginService = new LoginService();
    private static String PASSWORD_REGEX;
    private static String EMAIL_REGEX;

     {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("validation.properties")) {
            Properties props = new Properties();
            props.load(inputStream);
            PASSWORD_REGEX = props.getProperty("password");
            EMAIL_REGEX = props.getProperty("email");
        } catch (IOException e) {
            logger.error("Property file not found", e);
        }
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
         logger.info(PASSWORD_REGEX);
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
                pass = passwordEncoder.encode(pass);
                try {
                    logger.info("In try");
                    loginService.saveNewUser(User.builder().email(email).password(pass).role(User.Role.USER).build());
                    getFlashAttributesContainer(request).put("success", "registration");
                    return request.getContextPath() + "redirect:/home";
                } catch (Exception ex) {
                    logger.info(ex);
                    errors.add("creation error");
                    request.setAttribute("errors", errors);
                    return "/registration.jsp";
                }
            }
            request.setAttribute("errors", errors);
            return "/registration.jsp";
        }
        return "/registration.jsp";
    }
}
