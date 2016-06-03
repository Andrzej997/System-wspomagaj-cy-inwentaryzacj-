/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.reservations.client.views;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import pl.polsl.reservations.client.mediators.WeekDataViewMediator;
import pl.polsl.reservations.client.views.utils.ButtonStyle;
import pl.polsl.reservations.client.views.utils.DateLabelFormatter;
import pl.polsl.reservations.client.views.utils.PanelStyle;
import pl.polsl.reservations.client.views.utils.RoomComboBox;

/**
 *
 * @author Ola
 */
public class WeekDataView extends JPanel {

    MainView window;
    private JPanel chooseRoomDropdown;
    private JButton nextBtn;
    private JButton prevBtn;
    private JTable planTable;
    private JButton calendarBtn;
    private Calendar startDate;
    private Calendar endDate;
    private JDatePanelImpl datePanel;
    private JDatePickerImpl datePicker;
    private final Object selectedItem;
    private UtilDateModel model;
    private SimpleDateFormat dateFormat;
    private final transient WeekDataViewMediator weekDataViewMediator;

    public WeekDataView(MainView window, Object selectedItem,
            WeekDataViewMediator weekDataViewMediator) {
        this.window = window;
        this.weekDataViewMediator = weekDataViewMediator;
        initComponents();
        this.selectedItem = selectedItem;
        
    }

    private void initComponents() {
        chooseRoomDropdown = new RoomComboBox();
        nextBtn = new JButton();
        prevBtn = new JButton();
        calendarBtn = new JButton();
        planTable = new JTable();

        model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        endDate.add(Calendar.DATE, 6);
        dateFormat = new DateLabelFormatter().dateFormatter;
        datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        PanelStyle.setSize(datePanel, 220, 200);
        setDateText();
        //TODO - logika zmiany tygodnia
        try {
            Image img = ImageIO.read(getClass().getResource("/resources/left.png"));
            ButtonStyle.setStyle(prevBtn, img);
            Image img2 = ImageIO.read(getClass().getResource("/resources/right.png"));
            ButtonStyle.setStyle(nextBtn, img2);
            Image img3 = ImageIO.read(getClass().getResource("/resources/calendar.png"));
            ButtonStyle.setStyle(calendarBtn, img3);
        } catch (IOException ex) {
            System.out.println("RESOURCE ERROR: " + ex.toString());
        }

        prevBtn.addActionListener((ActionEvent e) -> {
             startDate.set(datePicker.getModel().getYear(), 
                    datePicker.getModel().getMonth(),
                    datePicker.getModel().getDay());
            startDate.add(Calendar.DATE, -1);
            endDate.set(datePicker.getModel().getYear(), 
                    datePicker.getModel().getMonth(),
                    datePicker.getModel().getDay());
            endDate.add(Calendar.DATE, 5);
            setDateText();
        });
        nextBtn.addActionListener((ActionEvent e) -> {
            startDate.set(datePicker.getModel().getYear(), 
                    datePicker.getModel().getMonth(),
                    datePicker.getModel().getDay());
            startDate.add(Calendar.DATE, 1);
            endDate.set(datePicker.getModel().getYear(), 
                    datePicker.getModel().getMonth(),
                    datePicker.getModel().getDay());
            endDate.add(Calendar.DATE, 7);
            setDateText();
        });
        initTable();
        PanelStyle.setSize(this, 800, 600);
        JPanel weekPanel = new JPanel();
        PanelStyle.setSize(weekPanel, 350, 40);
        BoxLayout weekLayout = new BoxLayout(weekPanel, BoxLayout.X_AXIS);
        weekPanel.setLayout(weekLayout);
        weekPanel.add(prevBtn);
        weekPanel.add(datePicker);
        weekPanel.add(nextBtn);

        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxlayout);

        add(weekPanel);
        add(chooseRoomDropdown);
        add(planTable);


        /*    chooseRoomDropdown.addActionListener((ActionEvent e) -> {
            if (chooseRoomDropdown.getSelectedItem() != null) {
                weekDataViewMediator.getReservations();
            }
        });
        chooseButton.addActionListener((ActionEvent e) -> {
            if (chooseRoomDropdown.getSelectedItem() != null) {
                weekDataViewMediator.getReservations();
            }
        });*/
    }

    private void initTable() {
        TableModel dataModel = new DefaultTableModel() {
            @Override
            public int getColumnCount() {
                return 7;
            }

            @Override
            public int getRowCount() {
                return 32;
            }

            @Override
            public Object getValueAt(int row, int column) {
                return row;
            }

        };
        planTable = new JTable(dataModel);
    }

    private void setDateText() {
        datePicker.getModel().setDay(startDate.get(Calendar.DAY_OF_MONTH));
          datePicker.getModel().setMonth(startDate.get(Calendar.MONTH));
            datePicker.getModel().setYear(startDate.get(Calendar.YEAR));
        datePicker
                .getJFormattedTextField()
                .setText(dateFormat
                        .format(startDate
                                .getTime()) + " - "
                        + dateFormat
                        .format(endDate.getTime()));
    }

    public MainView getWindow() {
        return window;
    }

    public void setWindow(MainView window) {
        this.window = window;
    }

    public JPanel getChooseRoomDropdown() {
        return chooseRoomDropdown;
    }

    public void setChooseRoomDropdown(JPanel chooseRoomDropdown) {
        this.chooseRoomDropdown = chooseRoomDropdown;
    }

    public JButton getNextBtn() {
        return nextBtn;
    }

    public void setNextBtn(JButton nextBtn) {
        this.nextBtn = nextBtn;
    }

    public JButton getPrevBtn() {
        return prevBtn;
    }

    public void setPrevBtn(JButton prevBtn) {
        this.prevBtn = prevBtn;
    }

    public JTable getPlanTable() {
        return planTable;
    }

    public void setPlanTable(JTable planTable) {
        this.planTable = planTable;
    }

    public JButton getCalendarBtn() {
        return calendarBtn;
    }

    public void setCalendarBtn(JButton calendarBtn) {
        this.calendarBtn = calendarBtn;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public JDatePanelImpl getDatePanel() {
        return datePanel;
    }

    public void setDatePanel(JDatePanelImpl datePanel) {
        this.datePanel = datePanel;
    }

    public JDatePickerImpl getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(JDatePickerImpl datePicker) {
        this.datePicker = datePicker;
    }

    public UtilDateModel getModel() {
        return model;
    }

    public void setModel(UtilDateModel model) {
        this.model = model;
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }
    
    

}
