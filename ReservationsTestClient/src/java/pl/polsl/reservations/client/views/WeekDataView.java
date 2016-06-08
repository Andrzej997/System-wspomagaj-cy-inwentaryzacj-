package pl.polsl.reservations.client.views;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.mediators.AddEditViewMediator;
import pl.polsl.reservations.client.mediators.DayDataViewMediator;
import pl.polsl.reservations.client.mediators.WeekDataViewMediator;
import pl.polsl.reservations.client.views.renderers.WeekCustomRenderer;
import pl.polsl.reservations.client.views.utils.ButtonStyle;
import pl.polsl.reservations.client.views.utils.WeekDateFormatter;
import pl.polsl.reservations.client.views.utils.DatePicker;
import pl.polsl.reservations.client.views.utils.PanelStyle;
import pl.polsl.reservations.client.views.utils.RoomComboBox;

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
    private JButton backBtn;
    private JTable planTable;
    private JButton calendarBtn;
    private Calendar startDate;
    private Calendar endDate;
    private final Object selectedItem;
    private SimpleDateFormat dateFormat;
    private final transient WeekDataViewMediator weekDataViewMediator;
    private DatePicker datePicker;

    public WeekDataView(MainView window, Object selectedItem,
            WeekDataViewMediator weekDataViewMediator) {
        this.window = window;
        this.weekDataViewMediator = weekDataViewMediator;
        initComponents();
        this.selectedItem = selectedItem;

    }

    private void initComponents() {
        initializeObjects();
        setupButtons();
        addListeners();
        initTable();
        setBorder(new EmptyBorder(10, 10, 30, 10));
        PanelStyle.setSize(this, 800, 600);
        setupLayouts();
        keyInputDispatcher();
        chooseRoomDropdown.addActionListener((ActionEvent e) -> {
            if (chooseRoomDropdown.getSelectedItem() != null) {
                chooseRoomDropdown.onAction();
                weekDataViewMediator.getReservations();
            }
        });
        window.checkPrivileges();
    }
    
    private void setupLayouts(){
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
        JScrollPane tableScrollPanel = new JScrollPane(planTable);
        PanelStyle.setSize(tableScrollPanel,780,500);
        PanelStyle.setSize(planTable,780,500);
        add(tableScrollPanel);
    }
    
    private void addListeners(){
         prevBtn.addActionListener((ActionEvent e) -> {
            onClickBtnPrevious(e);
        });
        nextBtn.addActionListener((ActionEvent e) -> {
            onClickBtnNext(e);
        });

        datePicker.getDatePicker().addActionListener((ActionEvent e) -> {
            datePickerChange(e);
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
        planTable = new JTable();
        datePicker = DatePicker.getInstance();
        backBtn = new JButton();
        startDate = Calendar.getInstance();
        startDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        endDate = Calendar.getInstance();
        endDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        dateFormat = new WeekDateFormatter().dateFormatter;
    }

    private void initTable() {
        DefaultTableModel dataModel = new DefaultTableModelImpl();
        String[] days = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        dataModel.setColumnIdentifiers(days);

        planTable = new JTable(dataModel);
        planTable.setDefaultRenderer(Object.class, new WeekCustomRenderer());

        planTable.addMouseListener(new MouseListenerImpl());
        planTable.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Integer column = planTable.columnAtPoint(e.getPoint());
                    if (column != 0) {
                        Calendar cal = startDate;
                        window.setView(new DayDataViewMediator().createView(window, cal));
                    }
                }
            }
        });

    }

    private void datePickerChange(ActionEvent e) {
        Calendar date = datePicker.getDate();
        if (date != null) {
            startDate.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE));
            startDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            endDate.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE));
            endDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        }
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
                Lookup.removeUserCertificate();
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
