package pl.polsl.reservations.client.views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.EventListenerList;
import pl.polsl.reservations.client.mediators.AddEditViewMediator;
import pl.polsl.reservations.client.views.utils.ButtonStyle;
import pl.polsl.reservations.client.views.utils.DatePicker;
import pl.polsl.reservations.client.views.utils.PanelStyle;

/**
 *
 * @author Ola
 */
public class AddEditView extends JPanel {

    private static final long serialVersionUID = -6676295764328716585L;
    private final int NORMAL_WIDTH = 150;
    private final int NORMAL_HEIGHT = 30;

    MainView window;
    private JPanel mainPanel;
    private JPanel addPanel;
    private JPanel dayTablePanel;
    private JPanel labelPanel;
    private JPanel dataPanel;
    private JPanel hourPanel;
    private JPanel navigatePanel;
    private DatePicker datepicker;

    private JButton addButton;
    private JButton editButton;
    private JButton discardButton;

    private JTable dayTable;

    private JComboBox roomCb;
    private JComboBox groupCb;
    private JComboBox hourStartCb;
    private JComboBox hourStopCb;
    private JTextField titleTf;
    private JComboBox teacherCb;

    private JLabel roomLabel;
    private JLabel groupLabel;
    private JLabel hourLabel;
    private JLabel dateLabel;
    private JLabel titleLabel;
    private JLabel teacherLabel;

    private Date date;

    private boolean edit;

    private final transient AddEditViewMediator addEditViewMediator;

    public AddEditView(MainView window, AddEditViewMediator addEditViewMediator, boolean edit) {
        super(new BorderLayout());
        this.window = window;
        this.addEditViewMediator = addEditViewMediator;
        this.edit = edit;
        initComponents();
    }

    private void initComponents() {
        initFields();
        setText();
        setSize();
        initListeners();
        keyInputDispatcher();
        initView();
    }

    private void initView() {
        setDataHourCb();
        hourPanel.add(hourStartCb);
        hourPanel.add(new JLabel(" - "));
        hourPanel.add(hourStopCb);
        dataPanel.add(roomCb);
        dataPanel.add(datepicker);
        dataPanel.add(hourPanel);
        dataPanel.add(groupCb);
        dataPanel.add(titleTf);
        dataPanel.add(teacherCb);
        labelPanel.add(roomLabel);
        labelPanel.add(dateLabel);
        labelPanel.add(hourLabel);
        labelPanel.add(groupLabel);
        labelPanel.add(titleLabel);
        labelPanel.add(teacherLabel);
        addPanel.add(labelPanel);
        addPanel.add(dataPanel);
        mainPanel.add(addPanel);
        mainPanel.add(dayTablePanel);
        add(mainPanel);
        add(navigatePanel);
    }

    private void initListeners() {
        roomCb.addActionListener((ActionEvent e) -> {
            if (roomCb.getSelectedItem() != null) {
                addEditViewMediator.setRoomNumber((Integer) roomCb.getSelectedItem());
                addEditViewMediator.getReservations();
            }
        });
    }

    private void initFields() {
        date = new Date();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        addPanel = new JPanel();
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.X_AXIS));
        dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        hourPanel = new JPanel();
        hourPanel.setLayout(new BoxLayout(hourPanel, BoxLayout.X_AXIS));
        navigatePanel = new JPanel();
        navigatePanel.setLayout(new BoxLayout(navigatePanel, BoxLayout.X_AXIS));
        dayTablePanel = new JPanel();
        addButton = new JButton();
        addButton.addActionListener((ActionEvent evt) -> {
            onOkClick(evt);
        });
        dayTable = new JTable(new DayTableModel(32, 3));
        dayTablePanel.add(new JScrollPane(dayTable));
        roomCb = new JComboBox();
        datepicker = DatePicker.getDayInstance();
        groupCb = new JComboBox();
        hourStartCb = new JComboBox();
        hourStopCb = new JComboBox();
        titleTf = new JTextField();
        teacherCb = new JComboBox();

        dateLabel = new JLabel("Date: ");
        hourLabel = new JLabel("Hour: ");
        groupLabel = new JLabel("Group: ");
        teacherLabel = new JLabel("Teacher: ");
        titleLabel = new JLabel("Title: ");
        roomLabel = new JLabel("Room ID: ");

        // addButton = new JButton();
        editButton = new JButton();
        discardButton = new JButton();
    }

    private void setText() {
        try {
            Image img = ImageIO.read(getClass().getResource("/resources/add.png"));
            ButtonStyle.setStyle(addButton, img);
            Image img2 = ImageIO.read(getClass().getResource("/resources/ok.png"));
            ButtonStyle.setStyle(editButton, img2);
            Image img3 = ImageIO.read(getClass().getResource("/resources/error.png"));
            ButtonStyle.setStyle(discardButton, img3);
            if (edit) {
                navigatePanel.add(editButton);
                navigatePanel.add(discardButton);
            } else {
                navigatePanel.add(addButton);
            }

        } catch (IOException ex) {
            System.out.println("RESOURCE ERROR: " + ex.toString());
        }
    }

    private void onOkClick(ActionEvent evt) {
        addEditViewMediator.addReservation();
    }

    private void setSize() {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        PanelStyle.setSize(this, 800, 600);
        PanelStyle.setSize(addPanel, 300, 530);
        PanelStyle.setSize(dayTablePanel, 500, 530);
        PanelStyle.setSize(roomLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(teacherLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(titleLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(groupLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(dateLabel, NORMAL_WIDTH, 40);
        PanelStyle.setSize(hourLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(roomCb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(datepicker, NORMAL_WIDTH, 40);
        PanelStyle.setSize(hourPanel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(teacherCb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(groupCb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(titleTf, NORMAL_WIDTH, NORMAL_HEIGHT);
    }

    private void setDataHourCb() {
        Integer hour = 0;
        Integer quarter = 0;
        for (int i = 0; i < 96; i++) {
            hour = i / 4;
            quarter = (i % 4) * 15;
            String hourString = hour.toString() + ":";
            if (quarter == 0) {
                hourString += "00";
            } else {
                hourString += quarter.toString();
            }
            hourStartCb.addItem(hourString);
            hourStopCb.addItem(hourString);
        }
        hourStartCb.setSelectedItem("8:00");
        hourStopCb.setSelectedItem("9:00");
    }

    private void keyInputDispatcher() {

        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        AbstractAction escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEditView.this.getWindow().dispose();
                System.exit(0);
            }
        };
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        actionMap.put("escape", escapeAction);
    }

    public MainView getWindow() {
        return window;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getAddPanel() {
        return addPanel;
    }

    public JPanel getDayTablePanel() {
        return dayTablePanel;
    }

    public JButton getOkButton() {
        return addButton;
    }

    public JTable getDayTable() {
        return dayTable;
    }

    public JPanel getAddLabelPanel() {
        return labelPanel;
    }

    public JPanel getAddDataPanel() {
        return dataPanel;
    }

    public JPanel getHourPanel() {
        return hourPanel;
    }

    public JComboBox getRoomCb() {
        return roomCb;
    }

    public JComboBox getGroupCb() {
        return groupCb;
    }

    public JComboBox getHourStartCb() {
        return hourStartCb;
    }

    public JComboBox getHourStopCb() {
        return hourStopCb;
    }

    public JTextField getTitleTf() {
        return titleTf;
    }

    public JComboBox getTeacherCb() {
        return teacherCb;
    }

    public EventListenerList getListenerList() {
        return listenerList;
    }

    public void setListenerList(EventListenerList listenerList) {
        this.listenerList = listenerList;
    }

    public DatePicker getDatepicker() {
        return datepicker;
    }

    public void setDatepicker(DatePicker datepicker) {
        this.datepicker = datepicker;
    }

}
