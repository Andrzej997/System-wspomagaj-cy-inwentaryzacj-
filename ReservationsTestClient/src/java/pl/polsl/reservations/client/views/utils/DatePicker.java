package pl.polsl.reservations.client.views.utils;

import java.awt.Component;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import org.jdatepicker.DateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 *
 * @author abienioszek
 */
public class DatePicker extends JPanel {

    private static final DatePicker weekDatepicker = new DatePicker(true);
    private static final DatePicker dayDatepicker = new DatePicker(false);
    private static final long serialVersionUID = 3512548187838571101L;
    private JDatePanelImpl datePanel;
    private JDatePickerImpl datePicker;
    private UtilDateModel model;

    private DatePicker(boolean day) {
        model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        datePanel = new JDatePanelImpl(model, p);
        if (!day) {
            datePicker = new JDatePickerImpl(datePanel, new WeekDateFormatter());
            PanelStyle.setSize(datePanel, 220, 200);
            add(datePicker);
        } else {
            datePicker = new JDatePickerImpl(datePanel, new DayDateFormatter());
            PanelStyle.setSize(datePanel, 220, 200);
            PanelStyle.setSize(datePicker, 150, 30);
            add(datePicker);
        }

    }

    public static DatePicker getInstance() {
        return weekDatepicker;
    }

    public static DatePicker getDayInstance() {
        return dayDatepicker;
    }

    public DateModel<?> getModel() {
        return datePicker.getModel();
    }

    public void setDate(Calendar date, boolean day) {
        if (!day) {
            model = new UtilDateModel(getDateFromCalendar(date));
            Properties p = new Properties();
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.year", "Year");
            datePanel = new JDatePanelImpl(model, p);
            datePicker = new JDatePickerImpl(datePanel, new WeekDateFormatter());
            PanelStyle.setSize(datePanel, 220, 200);
        } else {
            model = new UtilDateModel(getDateFromCalendar(date));
            Properties p = new Properties();
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.year", "Year");
            datePanel = new JDatePanelImpl(model, p);
            datePicker = new JDatePickerImpl(datePanel, new DayDateFormatter());
            PanelStyle.setSize(datePanel, 220, 200);
            PanelStyle.setSize(datePicker, 150, 30);
        }
    }

    public static Date getDateFromCalendar(Calendar date) {
        Date result = new Date(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE));
        return result;
    }

    public JFormattedTextField getJFormattedTextField() {
        return datePicker.getJFormattedTextField();
    }

    public JDatePickerImpl getDatePicker() {
        return this.datePicker;
    }

}
