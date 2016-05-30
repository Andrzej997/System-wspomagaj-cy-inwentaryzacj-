/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.reservations.client.views.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFormattedTextField.AbstractFormatter;

/**
 *
 * @author abienioszek
 */
public class DateLabelFormatter extends AbstractFormatter {

    private String datePattern = "dd-MM-yyyy";
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
