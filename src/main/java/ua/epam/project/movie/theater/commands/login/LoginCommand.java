package ua.epam.project.movie.theater.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.project.movie.theater.service.LoginService;
import ua.epam.project.movie.theater.database.entity.User;
import ua.epam.project.movie.theater.utils.passwordencoder.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {
    private final Logger logger = LogManager.getRootLogger();
    private final LoginService loginService = new LoginService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        logger.info(request.getMethod());
        if (request.getMethod().equals("POST")) {
            try {
                User user = loginService.checkUserInDb(User.builder()
                        .email(email).password(pass)
                        .build());
                if (passwordEncoder.matches(pass, user.getPassword())) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    return  request.getContextPath() + "redirect:" + "?success=true";
                }
            } catch (Exception ex) {
                request.setAttribute("error", "error.wrong.user");
                return "/login.jsp";
            }
            request.setAttribute("error", "error.wrong.password");
            request.setAttribute("email", email);
        }
        return "/login.jsp";
    }
}
