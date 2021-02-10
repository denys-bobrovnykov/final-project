package ua.epam.project.movie.theater.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.project.movie.theater.database.entity.User;
import ua.epam.project.movie.theater.service.LoginService;
import ua.epam.project.movie.theater.utils.passwordencoder.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class RegistrationCommand implements Command {
    private final Logger logger = LogManager.getLogger(RegistrationCommand.class);
    private final LoginService loginService = new LoginService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            List<String> errors = new ArrayList<>();
            String email = request.getParameter("email");
            String pass = request.getParameter("password");
            logger.info("{} {}", email, pass);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
            logger.info(pass.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{4,10}$"));

            if (!pass.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{4,10}$")) {
                errors.add("error.register.password");
            }
            if (!email.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
                errors.add("error.register.email");
            }
            if (email.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$") && pass.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{4,10}$")) {
                pass = passwordEncoder.encode(pass);
                try {
                    logger.info("In try");
                    loginService.saveNewUser(User.builder().email(email).password(pass).role(User.Role.USER).build());
                    return request.getContextPath() + "redirect:";
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
