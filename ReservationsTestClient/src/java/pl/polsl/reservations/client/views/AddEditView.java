package pl.polsl.reservations.client.views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Date;
import javax.swing.*;
import javax.swing.event.EventListenerList;
import pl.polsl.reservations.client.mediators.AddEditViewMediator;
import pl.polsl.reservations.client.mediators.SearchViewMediator;
import pl.polsl.reservations.client.views.utils.PanelStyle;

/**
 *
 * @author Ola
 */
//TODO weŸ zrób datepicker
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

    private final transient AddEditViewMediator addEditViewMediator;

    public AddEditView(MainView window, AddEditViewMediator addEditViewMediator) {
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
        keyInputDispatcher();
    }

    private void initFields() {
        mainPanel = new JPanel(new BorderLayout());
        addPanel = new JPanel(new GridLayout(1, 2));
        addDataPanel = new JPanel(new GridLayout(6, 1));
        addLabelPanel = new JPanel(new GridLayout(6, 1));
        hourPanel = new JPanel(new BorderLayout());
        dayTablePanel = new JPanel();
        searchPanel = new SearchViewMediator().createView(window);
        okButton = new JButton();
        okButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            onOkClick(evt);
        });
        dayTable = new JTable(new DayTableModel(32,3));
        dayTablePanel.add(new JScrollPane(dayTable));
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
        PanelStyle.setSize(this, 800, 600);
        PanelStyle.setSize(addPanel, 400, 285);
        PanelStyle.setSize(searchPanel, 400, 285);
        PanelStyle.setSize(dayTablePanel, 400, 560);

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
        setDataDateCb();
        setDataHourCb();
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

    private void setDataDateCb() {
        //TODO: rzeczywiste dane
        dateCb.addItem("111");
        dateCb.addItem("111");
        dateCb.addItem("111");
        dateCb.addItem("111");
    }

    private void setDataHourCb() {
        Integer hour = 0;
        Integer quarter = 0;
        for(int i = 0; i< 96; i++){
            hour = i/4;
            quarter = (i % 4)*15;
            String hourString = hour.toString() + ":";
            if(quarter == 0){
                hourString += "00";
            } else{
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

    public EventListenerList getListenerList() {
        return listenerList;
    }

    public void setListenerList(EventListenerList listenerList) {
        this.listenerList = listenerList;
    }

}
