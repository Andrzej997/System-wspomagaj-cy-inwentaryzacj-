package pl.polsl.reservations.client.views.utils;

import java.awt.event.ActionEvent;
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
public class CustomDatePicker extends JPanel {

    private static CustomDatePicker weekDatepicker;
    private static CustomDatePicker dayDatepicker;
    private static final long serialVersionUID = 3512548187838571101L;
    private final JDatePanelImpl datePanel;
    private final JDatePickerImpl datePicker;
    private final UtilDateModel model;

    private CustomDatePicker(boolean day) {
        model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        datePanel = new JDatePanelImpl(model, p);
        if (!day) {
            datePicker = new JDatePickerImpl(datePanel, new WeekDateFormatter());
            PanelStyle.setSize(datePanel, 220, 200);
            PanelStyle.setSize(datePicker, 200, 30);
            datePicker.addActionListener((ActionEvent e) -> {
                Date dat = (Date) datePicker.getModel().getValue();
                if (dat != null) {
                    Calendar date = Calendar.getInstance();
                    date.setTime(dat);
                    dayDatepicker.getDatePicker().getModel().setYear(date.get(Calendar.YEAR));
                    dayDatepicker.getDatePicker().getModel().setMonth(date.get(Calendar.MONTH));
                    dayDatepicker.getDatePicker().getModel().setDay(date.get(Calendar.DATE));
                    dayDatepicker.getDatePicker().getModel().setSelected(true);
                }
            });
            add(datePicker);
        } else {
            datePicker = new JDatePickerImpl(datePanel, new DayDateFormatter());
            PanelStyle.setSize(datePanel, 220, 200);
            PanelStyle.setSize(datePicker, 170, 30);
            datePicker.addActionListener((ActionEvent e) -> {
                Date dat = (Date) datePicker.getModel().getValue();
                if (dat != null) {
                    Calendar date = Calendar.getInstance();
                    date.setTime(dat);
                    weekDatepicker.getDatePicker().getModel().setYear(date.get(Calendar.YEAR));
                    weekDatepicker.getDatePicker().getModel().setMonth(date.get(Calendar.MONTH));
                    weekDatepicker.getDatePicker().getModel().setDay(date.get(Calendar.DATE));
                    weekDatepicker.getDatePicker().getModel().setSelected(true);
                }
            });
            add(datePicker);
        }
    }

    public static CustomDatePicker getInstance() {
        if (weekDatepicker == null) {
            weekDatepicker = new CustomDatePicker(false);
            if (dayDatepicker == null) {
                dayDatepicker = new CustomDatePicker(true);
            }
        }
        return weekDatepicker;
    }

    public static CustomDatePicker getDayInstance() {
        if (dayDatepicker == null) {
            dayDatepicker = new CustomDatePicker(true);
            if (weekDatepicker == null) {
                weekDatepicker = new CustomDatePicker(false);
            }
        }
        return dayDatepicker;
    }

    public DateModel<?> getModel() {
        return datePicker.getModel();
    }

    public void setDate(Calendar date) {
        if (date != null) {
            dayDatepicker.getDatePicker().getModel().setYear(date.get(Calendar.YEAR));
            dayDatepicker.getDatePicker().getModel().setMonth(date.get(Calendar.MONTH));
            dayDatepicker.getDatePicker().getModel().setDay(date.get(Calendar.DATE));
            dayDatepicker.getDatePicker().getModel().setSelected(true);
            weekDatepicker.getDatePicker().getModel().setYear(date.get(Calendar.YEAR));
            weekDatepicker.getDatePicker().getModel().setMonth(date.get(Calendar.MONTH));
            weekDatepicker.getDatePicker().getModel().setDay(date.get(Calendar.DATE));
            weekDatepicker.getDatePicker().getModel().setSelected(true);
        }
    }

    public Calendar getDate() {
        Date dat = (Date) getModel().getValue();
        Calendar result = Calendar.getInstance();
        if (dat != null) {
            Calendar date = Calendar.getInstance();
            date.setTime(dat);
            result.set(Calendar.YEAR, date.get(Calendar.YEAR));
            result.set(Calendar.MONTH, date.get(Calendar.MONTH));
            result.set(Calendar.DATE, date.get(Calendar.DATE));
        }
        return result;
    }

    public JFormattedTextField getJFormattedTextField() {
        return datePicker.getJFormattedTextField();
    }

    public JDatePickerImpl getDatePicker() {
        return this.datePicker;
    }

}
