package ua.project.movie.theater.tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Custom tag.
 * Displays LocalDate on a page in specified format.
 */
public class DateTag extends SimpleTagSupport {
    private LocalDate date;

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        out.println(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }
}
