package pl.polsl.reservations.client.views;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javafx.scene.control.DatePicker;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import pl.polsl.reservations.client.mediators.AddEditViewMediator;
import pl.polsl.reservations.client.mediators.DayDataViewMediator;
import pl.polsl.reservations.client.mediators.WeekDataViewMediator;

/**
 *
 * @author Ola
 */
public class WeekDataView extends JPanel {

    private static final long serialVersionUID = 1354395203575126802L;

    MainView window;
    private RoomComboBox chooseRoomDropdown;
    private JButton nextBtn;
    private JButton prevBtn;
    private JTable planTable;
    private JButton calendarBtn;
    private Calendar startDate;
    private Calendar endDate;
    private final Object selectedItem;
    private SimpleDateFormat dateFormat;
    private DatePicker datePicker;
    private final transient WeekDataViewMediator weekDataViewMediator;

    public WeekDataView(MainView window, Object selectedItem,
            WeekDataViewMediator weekDataViewMediator) {
        this.window = window;
        this.weekDataViewMediator = weekDataViewMediator;
        initComponents();
        this.selectedItem = selectedItem;
        datePicker = DatePicker.getInstance();

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
        startDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        endDate = Calendar.getInstance();
        endDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
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
            onClickBtnPrevious(e);
        });
        nextBtn.addActionListener((ActionEvent e) -> {
            onClickBtnNext(e);
        });

        datePicker.addActionListener((ActionEvent e) -> {
            datePickerChange(e);
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
        add(new JScrollPane(planTable));
        keyInputDispatcher();
        chooseRoomDropdown.addActionListener((ActionEvent e) -> {
            if (chooseRoomDropdown.getSelectedItem() != null) {
                chooseRoomDropdown.onAction();
                weekDataViewMediator.getReservations();
            }
        });
        window.checkPrivileges();
    }

    private void initTable() {
        DefaultTableModel dataModel = new DefaultTableModelImpl();
        String[] days = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        dataModel.setColumnIdentifiers(days);

        planTable = new JTable(dataModel);
        planTable.setDefaultRenderer(Object.class, new WeekCustomRenderer());

        planTable.addMouseListener(new MouseListenerImpl()
        );
        planTable.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Integer column = planTable.columnAtPoint(e.getPoint());
                    if (column != 0) {
                        Calendar cal = startDate;
                        cal.add(Calendar.DATE, column - 1);
                        // if (row == 0) {

                        window.setView(new DayDataViewMediator().createView(window, cal)); //sprawdzi? czy dobry dzie? tygodnia
                        //} else {
                        //    new AddEditViewMediator().createView(window);
                        // }

                    }
                }
            }
        });
    }

    private void datePickerChange(ActionEvent e) {
        weekDataViewMediator.getReservations();
    }

    private void onClickBtnNext(ActionEvent e) {
        startDate.set(datePicker.getModel().getYear(),
                datePicker.getModel().getMonth(),
                datePicker.getModel().getDay());
        startDate.add(Calendar.DATE, 7);
        int dayOfWeek = startDate.get(Calendar.DAY_OF_WEEK);
        dayOfWeek -= 2;
        startDate.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
        endDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH),
                startDate.get(Calendar.DATE));
        endDate.add(Calendar.DATE, 6);
        setDateText();

        weekDataViewMediator.getReservations();
    }

    private void onClickBtnPrevious(ActionEvent e) {
        startDate.set(datePicker.getModel().getYear(),
                datePicker.getModel().getMonth(),
                datePicker.getModel().getDay());
        startDate.add(Calendar.DAY_OF_MONTH, -7);
        int dayOfWeek = startDate.get(Calendar.DAY_OF_WEEK);
        dayOfWeek -= 2;
        startDate.add(Calendar.DAY_OF_MONTH, -dayOfWeek);

        endDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH),
                startDate.get(Calendar.DATE));
        endDate.add(Calendar.DATE, 6);
        setDateText();

        weekDataViewMediator.getReservations();
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

    private void keyInputDispatcher() {

        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        AbstractAction nextAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WeekDataView.this.onClickBtnNext(e);
            }
        };
        AbstractAction previousAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WeekDataView.this.onClickBtnPrevious(e);
            }
        };
        AbstractAction escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WeekDataView.this.getWindow().dispose();
                System.exit(0);
            }
        };
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "next");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "previous");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        actionMap.put("next", nextAction);
        actionMap.put("previous", previousAction);
        actionMap.put("escape", escapeAction);
    }

    public MainView getWindow() {
        return window;
    }

    public void setWindow(MainView window) {
        this.window = window;
    }

    public RoomComboBox getChooseRoomDropdown() {
        return chooseRoomDropdown;
    }

    public void setChooseRoomDropdown(RoomComboBox chooseRoomDropdown) {
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

    private static class DefaultTableModelImpl extends DefaultTableModel {

        public DefaultTableModelImpl() {
        }
        private static final long serialVersionUID = 7434377947963338162L;

        @Override
        public int getColumnCount() {
            return 8;
        }

        @Override
        public int getRowCount() {
            return 32;
        }

        @Override
        public Object getValueAt(int row, int column) {
            return row;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    private class MouseListenerImpl implements MouseListener {

        public MouseListenerImpl() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                Integer column = planTable.getSelectedColumn();
                Integer row = planTable.getSelectedRow();
                if (column != 0) {
                    Calendar cal = startDate;
                    cal.add(Calendar.DATE, column - 1);

                    window.setView(new AddEditViewMediator(cal, chooseRoomDropdown.getSelectedItem()).createView(window));
                }
//todo:
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}
