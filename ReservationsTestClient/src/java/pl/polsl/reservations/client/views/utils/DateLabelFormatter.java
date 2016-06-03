package pl.polsl.reservations.client.views.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFormattedTextField.AbstractFormatter;

/**
 *
 * @author abienioszek
 */
public class DateLabelFormatter extends AbstractFormatter {

    private static final long serialVersionUID = 775833091532800553L;

    private final String datePattern = "dd.MM.yyyy";
    public final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text.substring(0, 9));
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        String result = "";
        if (value != null) {
            Calendar cal = (Calendar) value;
            result += dateFormatter.format(cal.getTime()) + " - ";
            cal.add(Calendar.DATE, 6);
            result += dateFormatter.format(cal.getTime());
        }
        return result;
    }


}
