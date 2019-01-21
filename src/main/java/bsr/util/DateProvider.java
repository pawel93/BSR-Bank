package bsr.util;

import javax.ws.rs.WebApplicationException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateProvider {

    private String format;

    public DateProvider(String format){
        this.format = format;
    }

    public Date fromString(String value) {

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public String toString(Date date) {
        return new SimpleDateFormat(format).format(date);
    }

}
