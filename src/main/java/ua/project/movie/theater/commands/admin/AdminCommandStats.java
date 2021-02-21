package ua.project.movie.theater.commands.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.project.movie.theater.commands.Command;
import ua.project.movie.theater.database.DAOFactory;
import ua.project.movie.theater.exception.AppException;
import ua.project.movie.theater.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ua.project.movie.theater.commands.CommandUtility.getFlashAttributesContainer;

public class AdminCommandStats implements Command {
    private final Logger logger = LogManager.getLogger(AdminCommandStats.class);

    private final TicketService ticketService;

    public AdminCommandStats() {
        ticketService = new TicketService(DAOFactory.getDAOFactory().getTicketDAO());
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String dateStart = request.getParameter("date_start");
        String dateEnd = request.getParameter("date_end");
        List<String> errors = validatePeriod(dateStart, dateEnd);
        logger.info("{} {} {}", dateStart, dateEnd, errors);
        if (!errors.isEmpty()) {
            getFlashAttributesContainer(request).put("errors", errors);
            return request.getContextPath() + "redirect:/admin";
        }
        List<LocalDate> datesTuple = Stream.of(dateStart, dateEnd).map(LocalDate::parse).collect(Collectors.toList());
        try {
            getFlashAttributesContainer(request).put("stats", ticketService.getStats(datesTuple));
            getFlashAttributesContainer(request).put("period", datesTuple);
            return request.getContextPath() + "redirect:/admin";
        } catch (AppException ex) {
            logger.error(ex);
            return request.getContextPath() + "redirect:/admin";
        }
    }

    private List<String> validatePeriod(String dateStart, String dateEnd) {
        List<String> validationErrors = new ArrayList<>();
        try {
            LocalDate start = LocalDate.parse(dateStart);
            LocalDate end = LocalDate.parse(dateEnd);
            if (end.isBefore(start)) {
                validationErrors.add("error.start.before.end");
            }
        } catch (DateTimeParseException ex) {
            logger.error("Dates not valid");
            validationErrors.add("error.invalid.search.dates");
        }
        return validationErrors;
    }

}
