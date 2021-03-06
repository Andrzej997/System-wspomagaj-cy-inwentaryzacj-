package pl.polsl.reservations.client.views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.mediators.AddEditViewMediator;
import pl.polsl.reservations.client.mediators.WeekDataViewMediator;
import pl.polsl.reservations.client.views.utils.ButtonStyle;
import pl.polsl.reservations.client.views.utils.CustomDatePicker;
import pl.polsl.reservations.client.views.utils.DayTableModel;
import pl.polsl.reservations.client.views.utils.PanelStyle;
import pl.polsl.reservations.client.views.utils.RoomComboBox;
import pl.polsl.reservations.dto.ReservationDTO;

/**
 *
 * @author Ola
 */
public class AddEditView extends JPanel {

    private static final long serialVersionUID = -6676295764328716585L;
    private static final int NORMAL_WIDTH = 150;
    private static final int NORMAL_HEIGHT = 30;

    private MainView window;
    private JPanel mainPanel;
    private JPanel addPanel;
    private JPanel dayTablePanel;
    private JPanel labelPanel;
    private JPanel dataPanel;
    private JPanel hourPanel;
    private JPanel navigatePanel;
    private JPanel contentPanel;
    private CustomDatePicker datepicker;

    private JButton addButton;
    private JButton editButton;
    private JButton discardButton;
    private JButton backBtn;

    private JTable dayTable;

    private RoomComboBox roomCb;
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
    private JCheckBox isGeneralChb;

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
        mainPanel.add(backBtn);
        contentPanel.add(addPanel);
        contentPanel.add(isGeneralChb);
        mainPanel.add(contentPanel);
        mainPanel.add(navigatePanel);
        add(mainPanel);
        add(dayTablePanel);
    }

    private void initListeners() {
        roomCb.addActionListener((ActionEvent e) -> {
            if (roomCb.getSelectedItem() != null) {
                roomCb.onAction();
                addEditViewMediator.setRoomNumber(roomCb.getSelectedItem());
                if (!addEditViewMediator.ifChosenReservation() || !edit) {
                    addEditViewMediator.setChosenReservation(null);
                    addEditViewMediator.getReservations();
                }

            }
        });
        datepicker.getDatePicker().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datePickerChange(e);
            }
        });
        backBtn.addActionListener((ActionEvent e) -> {
            window.setView(new WeekDataViewMediator().createView(window, roomCb.getSelectedItem()));
        });
    }

    private void datePickerChange(ActionEvent e) {
        addEditViewMediator.setDate(datepicker.getDate());
        if (!addEditViewMediator.ifChosenReservation() || !edit) {
            addEditViewMediator.setChosenReservation(null);
            addEditViewMediator.getReservations();
        }
    }

    private void initFields() {
        date = new Date();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
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
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        dayTablePanel = new JPanel();
        addButton = new JButton();
        roomCb = new RoomComboBox();

        dayTable = new JTable(new DayTableModel(32, 3));
        JScrollPane tableScrollPanel = new JScrollPane(dayTable);
        PanelStyle.setSize(tableScrollPanel, 450, 550);
        dayTablePanel.add(tableScrollPanel);
        dayTable.addMouseListener(new MouseListenerImpl());
        datepicker = CustomDatePicker.getDayInstance();

        groupCb = new JComboBox();
        hourStartCb = new JComboBox();
        hourStopCb = new JComboBox();
        titleTf = new JTextField();
        teacherCb = new JComboBox();
        isGeneralChb = new JCheckBox("General");

        dateLabel = new JLabel("Date: ");
        hourLabel = new JLabel("Hour: ");
        groupLabel = new JLabel("Group: ");
        teacherLabel = new JLabel("Teacher: ");
        titleLabel = new JLabel("Title: ");
        roomLabel = new JLabel("Room ID: ");

        editButton = new JButton();
        discardButton = new JButton();
        backBtn = new JButton();

        addButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            onOkClick(evt);
        });
        editButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            onEditClick(evt);
        });
        discardButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            onDiscardClick(evt);
        });

    }

    private void setText() {
        try {
            Image img = ImageIO.read(AddEditView.class.getResource("/resources/add.png"));
            ButtonStyle.setStyle(addButton, img);
            Image img2 = ImageIO.read(AddEditView.class.getResource("/resources/ok.png"));
            ButtonStyle.setStyle(editButton, img2);
            Image img3 = ImageIO.read(AddEditView.class.getResource("/resources/error.png"));
            ButtonStyle.setStyle(discardButton, img3);
            Image img4 = ImageIO.read(AddEditView.class.getResource("/resources/back.png"));
            ButtonStyle.setStyle(backBtn, img4);
            backBtn.setBorder(new EmptyBorder(0, 0, 0, 260));
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
        PanelStyle.setSize(addPanel, 330, 200);
        contentPanel.setBorder(new EmptyBorder(100, 0, 100, 0));
        PanelStyle.setSize(contentPanel, 330, 480);
        PanelStyle.setSize(dayTablePanel, 450, 550);
        PanelStyle.setSize(roomLabel, NORMAL_WIDTH - 30, NORMAL_HEIGHT);
        PanelStyle.setSize(teacherLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(titleLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(groupLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(dateLabel, NORMAL_WIDTH, 40);
        PanelStyle.setSize(hourLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(roomCb, NORMAL_WIDTH + 30, NORMAL_HEIGHT);
        PanelStyle.setSize(datepicker, NORMAL_WIDTH + 20, 40);
        PanelStyle.setSize(hourPanel, NORMAL_WIDTH + 20, NORMAL_HEIGHT);
        PanelStyle.setSize(teacherCb, NORMAL_WIDTH + 20, NORMAL_HEIGHT);
        PanelStyle.setSize(groupCb, NORMAL_WIDTH + 20, NORMAL_HEIGHT);
        PanelStyle.setSize(titleTf, NORMAL_WIDTH + 20, NORMAL_HEIGHT);
        PanelStyle.setSize(isGeneralChb, NORMAL_WIDTH + 20, NORMAL_HEIGHT);
    }

    private void setDataHourCb() {
        for (int i = 0; i < 96; i++) {
            Integer hour = i / 4;
            Integer quarter = (i % 4) * 15;
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
                Lookup.removeUserCertificate();
                System.exit(0);
            }
        };
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        actionMap.put("escape", escapeAction);
    }

    /**
     * @return the window
     */
    public MainView getWindow() {
        return window;
    }

    /**
     * @param window the window to set
     */
    public void setWindow(MainView window) {
        this.window = window;
    }

    /**
     * @return the mainPanel
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * @param mainPanel the mainPanel to set
     */
    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    /**
     * @return the addPanel
     */
    public JPanel getAddPanel() {
        return addPanel;
    }

    /**
     * @param addPanel the addPanel to set
     */
    public void setAddPanel(JPanel addPanel) {
        this.addPanel = addPanel;
    }

    /**
     * @return the dayTablePanel
     */
    public JPanel getDayTablePanel() {
        return dayTablePanel;
    }

    /**
     * @param dayTablePanel the dayTablePanel to set
     */
    public void setDayTablePanel(JPanel dayTablePanel) {
        this.dayTablePanel = dayTablePanel;
    }

    /**
     * @return the labelPanel
     */
    public JPanel getLabelPanel() {
        return labelPanel;
    }

    /**
     * @param labelPanel the labelPanel to set
     */
    public void setLabelPanel(JPanel labelPanel) {
        this.labelPanel = labelPanel;
    }

    /**
     * @return the dataPanel
     */
    public JPanel getDataPanel() {
        return dataPanel;
    }

    /**
     * @param dataPanel the dataPanel to set
     */
    public void setDataPanel(JPanel dataPanel) {
        this.dataPanel = dataPanel;
    }

    /**
     * @return the hourPanel
     */
    public JPanel getHourPanel() {
        return hourPanel;
    }

    /**
     * @param hourPanel the hourPanel to set
     */
    public void setHourPanel(JPanel hourPanel) {
        this.hourPanel = hourPanel;
    }

    /**
     * @return the navigatePanel
     */
    public JPanel getNavigatePanel() {
        return navigatePanel;
    }

    /**
     * @param navigatePanel the navigatePanel to set
     */
    public void setNavigatePanel(JPanel navigatePanel) {
        this.navigatePanel = navigatePanel;
    }

    /**
     * @return the datepicker
     */
    public CustomDatePicker getDatepicker() {
        return datepicker;
    }

    /**
     * @param datepicker the datepicker to set
     */
    public void setDatepicker(CustomDatePicker datepicker) {
        this.datepicker = datepicker;
    }

    /**
     * @return the addButton
     */
    public JButton getAddButton() {
        return addButton;
    }

    /**
     * @param addButton the addButton to set
     */
    public void setAddButton(JButton addButton) {
        this.addButton = addButton;
    }

    /**
     * @return the editButton
     */
    public JButton getEditButton() {
        return editButton;
    }

    /**
     * @param editButton the editButton to set
     */
    public void setEditButton(JButton editButton) {
        this.editButton = editButton;
    }

    /**
     * @return the discardButton
     */
    public JButton getDiscardButton() {
        return discardButton;
    }

    /**
     * @param discardButton the discardButton to set
     */
    public void setDiscardButton(JButton discardButton) {
        this.discardButton = discardButton;
    }

    /**
     * @return the dayTable
     */
    public JTable getDayTable() {
        return dayTable;
    }

    /**
     * @param dayTable the dayTable to set
     */
    public void setDayTable(JTable dayTable) {
        this.dayTable = dayTable;
    }

    /**
     * @return the roomCb
     */
    public RoomComboBox getRoomCb() {
        return roomCb;
    }

    /**
     * @param roomCb the roomCb to set
     */
    public void setRoomCb(RoomComboBox roomCb) {
        this.roomCb = roomCb;
    }

    /**
     * @return the groupCb
     */
    public JComboBox getGroupCb() {
        return groupCb;
    }

    /**
     * @param groupCb the groupCb to set
     */
    public void setGroupCb(JComboBox groupCb) {
        this.groupCb = groupCb;
    }

    /**
     * @return the hourStartCb
     */
    public JComboBox getHourStartCb() {
        return hourStartCb;
    }

    /**
     * @param hourStartCb the hourStartCb to set
     */
    public void setHourStartCb(JComboBox hourStartCb) {
        this.hourStartCb = hourStartCb;
    }

    /**
     * @return the hourStopCb
     */
    public JComboBox getHourStopCb() {
        return hourStopCb;
    }

    /**
     * @param hourStopCb the hourStopCb to set
     */
    public void setHourStopCb(JComboBox hourStopCb) {
        this.hourStopCb = hourStopCb;
    }

    /**
     * @return the titleTf
     */
    public JTextField getTitleTf() {
        return titleTf;
    }

    /**
     * @param titleTf the titleTf to set
     */
    public void setTitleTf(JTextField titleTf) {
        this.titleTf = titleTf;
    }

    /**
     * @return the teacherCb
     */
    public JComboBox getTeacherCb() {
        return teacherCb;
    }

    /**
     * @param teacherCb the teacherCb to set
     */
    public void setTeacherCb(JComboBox teacherCb) {
        this.teacherCb = teacherCb;
    }

    /**
     * @return the roomLabel
     */
    public JLabel getRoomLabel() {
        return roomLabel;
    }

    /**
     * @param roomLabel the roomLabel to set
     */
    public void setRoomLabel(JLabel roomLabel) {
        this.roomLabel = roomLabel;
    }

    /**
     * @return the groupLabel
     */
    public JLabel getGroupLabel() {
        return groupLabel;
    }

    /**
     * @param groupLabel the groupLabel to set
     */
    public void setGroupLabel(JLabel groupLabel) {
        this.groupLabel = groupLabel;
    }

    /**
     * @return the hourLabel
     */
    public JLabel getHourLabel() {
        return hourLabel;
    }

    /**
     * @param hourLabel the hourLabel to set
     */
    public void setHourLabel(JLabel hourLabel) {
        this.hourLabel = hourLabel;
    }

    /**
     * @return the dateLabel
     */
    public JLabel getDateLabel() {
        return dateLabel;
    }

    /**
     * @param dateLabel the dateLabel to set
     */
    public void setDateLabel(JLabel dateLabel) {
        this.dateLabel = dateLabel;
    }

    /**
     * @return the titleLabel
     */
    public JLabel getTitleLabel() {
        return titleLabel;
    }

    /**
     * @param titleLabel the titleLabel to set
     */
    public void setTitleLabel(JLabel titleLabel) {
        this.titleLabel = titleLabel;
    }

    /**
     * @return the teacherLabel
     */
    public JLabel getTeacherLabel() {
        return teacherLabel;
    }

    /**
     * @param teacherLabel the teacherLabel to set
     */
    public void setTeacherLabel(JLabel teacherLabel) {
        this.teacherLabel = teacherLabel;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the edit
     */
    public boolean isEdit() {
        return edit;
    }

    public JButton getBackBtn() {
        return backBtn;
    }

    public void setBackBtn(JButton backBtn) {
        this.backBtn = backBtn;
    }

    public JCheckBox getIsGeneralChb() {
        return isGeneralChb;
    }

    public void setIsGeneralChb(JCheckBox isGeneralChb) {
        this.isGeneralChb = isGeneralChb;
    }

    public Boolean isGeneral() {
        return isGeneralChb.isSelected();
    }

    /**
     * @param edit the edit to set
     */
    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    private void onEditClick(ActionEvent evt) {
        addEditViewMediator.editReservation();
    }

    private void onDiscardClick(ActionEvent evt) {
        addEditViewMediator.deleteReservation();
    }

    private class MouseListenerImpl implements MouseListener {

        public MouseListenerImpl() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            Integer column = dayTable.getSelectedColumn();
            Integer row = dayTable.getSelectedRow();
            if (e.getClickCount() == 2) {

                Calendar cal = datepicker.getDate();

                if (!edit) {
                    window.setView(new AddEditViewMediator(cal, roomCb.getSelectedItem(), addEditViewMediator.getChosenReservation()).createView(window, true));
                }

            }
            if (e.getClickCount() == 1) {
                List<ReservationDTO> reservations = addEditViewMediator.getReservationsList();
                Integer dayOfWeek = datepicker.getDate().get(Calendar.DAY_OF_WEEK);
                if (dayOfWeek == 1) {
                    dayOfWeek = 6;
                } else {
                    dayOfWeek -= 2;
                }

                Integer selectedTime = dayOfWeek * 96 + column * 32 + row;

                addEditViewMediator.setChosenReservation(null);

                for (ReservationDTO reservation : reservations) {
                    Integer startTime = reservation.getStartTime();
                    Integer endTime = reservation.getEndTime();

                    if (selectedTime >= startTime && selectedTime <= endTime) {
                        hourStartCb.setSelectedIndex(startTime - dayOfWeek * 96);
                        hourStopCb.setSelectedIndex(endTime - dayOfWeek * 96 + 1);
                        teacherCb.setSelectedItem(addEditViewMediator.getUserName(reservation.getUserId()));
                        groupCb.setSelectedItem(reservation.getType());
                        // if(reservation.getEndTime()<=7*96){
                        //     isGeneralChb.setSelected(true);
                        // }else{
                        //    isGeneralChb.setSelected(false);
                        //}
                        addEditViewMediator.setChosenReservation(reservation);

                    }
                }
                addEditViewMediator.refreshTableAfterChoose();
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
