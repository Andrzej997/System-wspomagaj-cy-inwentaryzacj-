
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.reservations.client.views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;
import javax.swing.*;
import pl.polsl.reservations.client.mediators.AddEditViewMediator;

/**
 *
 * @author Ola
 */
public class AddEditView extends JPanel {

    private static final long serialVersionUID = -6676295764328716585L;

    MainView window;
    private JPanel mainPanel;
    private JPanel addPanel;
    private JPanel searchPanel;
    private JPanel dayTablePanel;
    private JButton okButton;
    private JTable dayTable;
    private JPanel addLabelPanel;
    private JPanel addDataPanel;
    private JPanel hourPanel;
    private JComboBox roomCb;
    private JComboBox dateCb;
    private JComboBox groupCb;
    private JComboBox hourStartCb;
    private JComboBox hourStopCb;
    private JTextField titleTf;
    private JComboBox teacherCb;
    
    private AddEditViewMediator addEditViewMediator;

    public AddEditView(MainView window,AddEditViewMediator addEditViewMediator) {
        super(new BorderLayout());
        this.window = window;
        this.addEditViewMediator = addEditViewMediator;
        initComponents();
    }

    private void initComponents() {
        //set Date
        Date date = new Date();
        initFields();
        setText();
        setSize();
        initLabelPanel();
        initDataPanel();

        addPanel.add(addLabelPanel, BorderLayout.WEST);
        addPanel.add(addDataPanel, BorderLayout.WEST);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(addPanel, BorderLayout.NORTH);
        leftPanel.add(searchPanel, BorderLayout.SOUTH);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(dayTablePanel, BorderLayout.EAST);

        add(mainPanel, BorderLayout.NORTH);
        add(okButton, BorderLayout.SOUTH);
    }

    private void initFields() {
        mainPanel = new JPanel(new BorderLayout());
        addPanel = new JPanel(new GridLayout(1, 2));
        addDataPanel = new JPanel(new GridLayout(6, 1));
        addLabelPanel = new JPanel(new GridLayout(6, 1));
        hourPanel = new JPanel(new BorderLayout());
        dayTablePanel = new JPanel();
        searchPanel = new SearchView(window);
        okButton = new JButton();
        okButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            onOkClick(evt);
        });
        dayTable = new JTable(new DayTableModel());
        dayTablePanel.add(dayTable);
        roomCb = new JComboBox();
        dateCb = new JComboBox();
        groupCb = new JComboBox();
        hourStartCb = new JComboBox();
        hourStopCb = new JComboBox();
        titleTf = new JTextField();
        teacherCb = new JComboBox();
    }

    private void setText() {
        okButton.setText("ADD");
    }

    private void onOkClick(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Not supported yet");
    }

    private void setSize() {
        setMaximumSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(800, 600));
        setPreferredSize(new Dimension(800, 600));
        setSize(addPanel, new Dimension(400, 285));
        setSize(searchPanel, new Dimension(400, 285));
        setSize(dayTablePanel, new Dimension(400, 560));
    }

    private void setSize(JPanel panel, Dimension dimension) {
        panel.setMaximumSize(dimension);
        panel.setMinimumSize(dimension);
        panel.setPreferredSize(dimension);
    }

    private void initLabelPanel() {
        addLabelPanel.add(new JLabel("ROOM: "));
        addLabelPanel.add(new JLabel("DATE: "));
        addLabelPanel.add(new JLabel("HOUR: "));
        addLabelPanel.add(new JLabel("TARGET: "));
        addLabelPanel.add(new JLabel("TITLE: "));
        addLabelPanel.add(new JLabel("TEACHER: "));
    }

    private void initDataPanel() {
        setDataRoomCb();
        setDataDateCb();
        setDataGroupCb();
        setDataHourCb();
        setDataTeacherCb();
        JPanel hourPanel = new JPanel(new BorderLayout());
        hourPanel.add(hourStartCb, BorderLayout.WEST);
        hourPanel.add(hourStopCb, BorderLayout.EAST);
        hourPanel.add(new JLabel(" - "), BorderLayout.CENTER);
        addDataPanel.add(roomCb);
        addDataPanel.add(dateCb);
        addDataPanel.add(hourPanel);
        addDataPanel.add(groupCb);
        addDataPanel.add(titleTf);
        addDataPanel.add(teacherCb);
    }

    private void setDataRoomCb() {
        addEditViewMediator.getRooms();
    }

    private void setDataDateCb() {
        //TODO: rzeczywiste dane
        dateCb.addItem("111");
        dateCb.addItem("111");
        dateCb.addItem("111");
        dateCb.addItem("111");
    }

    private void setDataGroupCb() {
        //TODO: rzeczywiste dane
        groupCb.addItem("111");
        groupCb.addItem("111");
        groupCb.addItem("111");
        groupCb.addItem("111");
    }

    private void setDataHourCb() {
        //TODO: rzeczywiste dane
        hourStartCb.addItem("111");
        hourStartCb.addItem("111");
        hourStopCb.addItem("111");
        hourStopCb.addItem("111");
    }

    private void setDataTeacherCb() {
        //TODO: rzeczywiste dane
        teacherCb.addItem("111");
        teacherCb.addItem("111");
        teacherCb.addItem("111");
        teacherCb.addItem("111");
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

    public JPanel getSearchPanel() {
        return searchPanel;
    }

    public JPanel getDayTablePanel() {
        return dayTablePanel;
    }

    public JButton getOkButton() {
        return okButton;
    }

    public JTable getDayTable() {
        return dayTable;
    }

    public JPanel getAddLabelPanel() {
        return addLabelPanel;
    }

    public JPanel getAddDataPanel() {
        return addDataPanel;
    }

    public JPanel getHourPanel() {
        return hourPanel;
    }

    public JComboBox getRoomCb() {
        return roomCb;
    }

    public JComboBox getDateCb() {
        return dateCb;
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

}