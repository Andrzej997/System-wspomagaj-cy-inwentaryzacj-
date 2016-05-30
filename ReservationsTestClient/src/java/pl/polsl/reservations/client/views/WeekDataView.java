/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.reservations.client.views;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;
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

    private final Object selectedItem;

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
     
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        SimpleDateFormat dateFormat = new DateLabelFormatter().dateFormatter;
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
     //   model.setDate(Calendar.getInstance().getTime());
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        PanelStyle.setSize(datePanel, 240, 200);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DATE, 6);
        datePicker
                .getJFormattedTextField()
                .setText(dateFormat
                        .format(Calendar
                                .getInstance()
                                .getTime()) + " - " + 
                       endDate.getTime());
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
          
        });
        nextBtn.addActionListener((ActionEvent e) -> {
           
        });
   
        initTable();
        PanelStyle.setSize(this, 800, 600);
        JPanel weekPanel = new JPanel();
        PanelStyle.setSize(weekPanel, 400, 40);
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

    public void setPlanView(JTable planView) {
        this.planTable = planView;
    }

    public MainView getWindow() {
        return window;
    }

    public JPanel getChooseRoomDropdown() {
        return chooseRoomDropdown;
    }

    public JButton getNextWeek() {
        return nextBtn;
    }

    public JButton getPrevWeek() {
        return prevBtn;
    }

    public JTable getPlanView() {
        return planTable;
    }
    public Object getSelectedItem() {
        return selectedItem;
    }

}
