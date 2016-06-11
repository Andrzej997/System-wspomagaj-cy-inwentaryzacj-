package pl.polsl.reservations.client.views;

import pl.polsl.reservations.client.views.utils.DayTableModel;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import pl.polsl.reservations.client.mediators.DayDataViewMediator;
import pl.polsl.reservations.client.mediators.WeekDataViewMediator;
import pl.polsl.reservations.client.views.utils.ButtonStyle;
import pl.polsl.reservations.client.views.utils.CustomDatePicker;
import pl.polsl.reservations.client.views.utils.PanelStyle;
import pl.polsl.reservations.client.views.utils.RoomComboBox;
import pl.polsl.reservations.client.views.utils.WeekDateFormatter;

/**
 *
 * @author Ola
 */
public class DayDataView extends javax.swing.JPanel {

    private static final long serialVersionUID = 649020928680112340L;

    private final MainView window;
    private final Calendar date;

    private RoomComboBox chooseRoomDropdown;
    private JButton nextBtn;
    private JButton prevBtn;
    private JButton backBtn;
    private JTable planTable;
    private JButton calendarBtn;
    private Calendar startDate;
    private Calendar endDate;
    private SimpleDateFormat dateFormat;
    private CustomDatePicker datePicker;

    private final transient DayDataViewMediator dayDataViewMediator;

    public DayDataView(MainView window, Calendar date, DayDataViewMediator dayDataViewMediator) {
        this.window = window;
        this.date = date;
        this.dayDataViewMediator = dayDataViewMediator;
        initComponents();

    }

    private void initComponents() {
        initializeObjects();
        setupButtons();
        setupListeners();
        setBorder(new EmptyBorder(10, 10, 30, 10));
        PanelStyle.setSize(this, 800, 600);
        setupLayouts();
        keyInputDispatcher();
        window.checkPrivileges();
    }

    private void setupLayouts() {
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxlayout);

        JPanel emptyPanel = new JPanel();
        PanelStyle.setSize(emptyPanel, 225, 40);
        JPanel emptyPanel2 = new JPanel();
        PanelStyle.setSize(emptyPanel2, 145, 40);

        JPanel weekPanel = new JPanel();
        PanelStyle.setSize(weekPanel, 350, 40);
        weekPanel.setLayout(new BoxLayout(weekPanel, BoxLayout.X_AXIS));
        weekPanel.add(prevBtn);
        weekPanel.add(datePicker);
        weekPanel.add(nextBtn);

        JPanel navPanel = new JPanel();
        PanelStyle.setSize(navPanel, 800, 40);
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.X_AXIS));

        navPanel.add(backBtn);
        navPanel.add(emptyPanel2);
        navPanel.add(weekPanel);
        navPanel.add(emptyPanel);

        add(navPanel);
        add(chooseRoomDropdown);
        add(new JScrollPane(planTable));
    }

    private void setupListeners() {
        backBtn.addActionListener((ActionEvent e) -> {
             window.setView(new WeekDataViewMediator().createView(window, chooseRoomDropdown.getSelectedItem()));
        });

        prevBtn.addActionListener((ActionEvent e) -> {
            onClickBtnPrevious(e);
        });
        nextBtn.addActionListener((ActionEvent e) -> {
            onClickBtnNext(e);
        });

        datePicker.getDatePicker().addActionListener((ActionEvent e) -> {
            datePickerChange(e);
        });
        chooseRoomDropdown.addActionListener((ActionEvent e) -> {
            if (chooseRoomDropdown.getSelectedItem() != null) {
                chooseRoomDropdown.onAction();
                dayDataViewMediator.getReservations();
            }
        });
    }

    private void setupButtons() {
        try {
            Image img = ImageIO.read(getClass().getResource("/resources/left.png"));
            ButtonStyle.setStyle(prevBtn, img);
            Image img2 = ImageIO.read(getClass().getResource("/resources/right.png"));
            ButtonStyle.setStyle(nextBtn, img2);
            Image img3 = ImageIO.read(getClass().getResource("/resources/calendar.png"));
            ButtonStyle.setStyle(calendarBtn, img3);
            Image img4 = ImageIO.read(getClass().getResource("/resources/back.png"));
            ButtonStyle.setStyle(backBtn, img4);
        } catch (IOException ex) {
            System.out.println("RESOURCE ERROR: " + ex.toString());
        }
    }

    private void initializeObjects() {
        chooseRoomDropdown = new RoomComboBox();
        nextBtn = new JButton();
        prevBtn = new JButton();
        calendarBtn = new JButton();
        planTable = new JTable(new DayTableModel(32, 3));
        datePicker = CustomDatePicker.getInstance();
        datePicker.setDate(date);
        backBtn = new JButton();
        startDate = Calendar.getInstance();
        startDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        endDate = Calendar.getInstance();
        endDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        dateFormat = new WeekDateFormatter().dateFormatter;
    }

    private void keyInputDispatcher() {

        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        AbstractAction escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DayDataView.this.getWindow().dispose();
                System.exit(0);
            }
        };
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        actionMap.put("escape", escapeAction);
    }

    private void datePickerChange(ActionEvent e) {
        Calendar data = datePicker.getDate();
        if (data != null) {
            startDate.set(data.get(Calendar.YEAR), data.get(Calendar.MONTH), data.get(Calendar.DATE));
            startDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            endDate.set(data.get(Calendar.YEAR), data.get(Calendar.MONTH), data.get(Calendar.DATE));
            endDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        }
        dayDataViewMediator.setDate(data);
        dayDataViewMediator.getReservations();
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

        dayDataViewMediator.getReservations();
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

        dayDataViewMediator.getReservations();
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

    public Object getDate() {
        return date;
    }

    public JButton getNextWeek() {
        return nextBtn;
    }

    public JButton getPrevWeek() {
        return prevBtn;
    }

    public JButton getBackButton() {
        return backBtn;
    }

    public JTable getPlanView() {
        return planTable;
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

    public JButton getBackBtn() {
        return backBtn;
    }

    public void setBackBtn(JButton backBtn) {
        this.backBtn = backBtn;
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

    public CustomDatePicker getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(CustomDatePicker datePicker) {
        this.datePicker = datePicker;
    }

}
