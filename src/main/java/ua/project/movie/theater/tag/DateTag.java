package ua.project.movie.theater.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTag extends SimpleTagSupport {
    private LocalDate date;

    public void setDate(LocalDate date) {
        this.date = date;
    }
    StringWriter sw = new StringWriter();
    public void doTag()

            throws JspException, IOException {
        if (date != null) {
            /* Use message from attribute */
            JspWriter out = getJspContext().getOut();
            out.println( date.format(DateTimeFormatter.ofPattern("dd.MM.YYYY")) );
        } else {
            /* use message from the body */
            getJspBody().invoke(sw);
            getJspContext().getOut().println(sw.toString());
        }
    }
}
