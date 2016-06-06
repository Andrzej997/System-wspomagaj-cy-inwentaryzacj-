/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.reservations.client.views.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private static DatePicker weekDatepicker;
    private static DatePicker dayDatepicker;
    private final JDatePanelImpl datePanel;
    private final JDatePickerImpl datePicker;
    private final UtilDateModel model;

    public DatePicker(boolean day) {
        model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        datePanel = new JDatePanelImpl(model, p);
       if(!day)
        {
        datePicker = new JDatePickerImpl(datePanel, new WeekDateFormatter());
        PanelStyle.setSize(datePanel, 220, 200);
        add(datePicker);
        weekDatepicker = this;
        }
       else {
        datePicker = new JDatePickerImpl(datePanel, new DayDateFormatter());
        PanelStyle.setSize(datePanel, 220, 200);
        PanelStyle.setSize(datePicker, 150,30);
        add(datePicker);
        dayDatepicker = this; 
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

    public JFormattedTextField getJFormattedTextField() {
        return datePicker.getJFormattedTextField();
    }
    
    public JDatePickerImpl getDatePicker(){
        return this.datePicker;
    }

    

}
