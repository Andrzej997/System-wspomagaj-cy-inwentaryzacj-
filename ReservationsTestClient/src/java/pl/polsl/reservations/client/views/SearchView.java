package pl.polsl.reservations.client.views;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;
import pl.polsl.reservations.client.mediators.SearchViewMediator;

/**
 *
 * @author Ola
 */

//TODO weŸ zrób datepicker
public class SearchView extends JPanel {

    private static final long serialVersionUID = 6265110752316154972L;

    private MainView window;
    private JPanel mainLayout;
    private JPanel dataLayout;
    private JPanel navPanel;
    private JPanel searchPanel;
    private JPanel searchLabelPanel;
    private JPanel searchDataPanel;
    private JComboBox dateCb;
    private JComboBox typeCb;
    private JComboBox hourStartCb;
    private JComboBox hourStopCb;
    private JTextField studentsTf;
    private JLabel responseTf;
    private JButton responseBtn;
    
    private final SearchViewMediator searchViewMediator;

    public SearchView(MainView window, SearchViewMediator searchViewMediator) {
        super(new BorderLayout());
        this.window = window;
        this.searchViewMediator = searchViewMediator;
        initComponents();

    }

    private void initComponents() {
        initFields();
        initSearchDataPanel();
        initSearchLabelPanel();

        GridBagConstraints position = new GridBagConstraints();

        searchPanel.add(searchLabelPanel);
        searchPanel.add(searchDataPanel);
        navPanel.add(responseBtn, BorderLayout.WEST);
        navPanel.add(responseTf, BorderLayout.EAST);
        dataLayout.add(searchPanel, BorderLayout.NORTH);
        dataLayout.add(navPanel, BorderLayout.SOUTH);
        mainLayout.add(dataLayout, position);
        add(mainLayout, BorderLayout.CENTER);

    }

    private void initFields() {
        mainLayout = new JPanel(new GridBagLayout());
        dataLayout = new JPanel(new BorderLayout());
        searchPanel = new JPanel(new GridLayout(1, 2));
        navPanel = new JPanel(new BorderLayout());
        searchDataPanel = new JPanel(new GridLayout(4, 1));
        searchLabelPanel = new JPanel(new GridLayout(4, 1));
        dateCb = new JComboBox();
        typeCb = new JComboBox();
        hourStartCb = new JComboBox();
        hourStopCb = new JComboBox();
        studentsTf = new JTextField();
        responseTf = new JLabel();
        responseBtn = new JButton();
        responseBtn.setText("SEARCH");
        responseBtn.addActionListener((java.awt.event.ActionEvent evt) -> {
            onOkClick(evt);
        });
        keyInputDispatcher();
    }

    private void initSearchDataPanel() {
        setDataDateCb();
        setDataHourCb();
        JPanel hourPanel = new JPanel(new BorderLayout());
        hourPanel.add(hourStartCb, BorderLayout.WEST);
        hourPanel.add(hourStopCb, BorderLayout.EAST);
        hourPanel.add(new JLabel(" - "), BorderLayout.CENTER);

        searchDataPanel.add(dateCb);
        searchDataPanel.add(hourPanel);
        searchDataPanel.add(studentsTf);
        searchDataPanel.add(typeCb);
    }

    private void initSearchLabelPanel() {
        searchLabelPanel.add(new JLabel("DATE"));
        searchLabelPanel.add(new JLabel("HOUR START"));
        searchLabelPanel.add(new JLabel("STUDENTS: "));
        searchLabelPanel.add(new JLabel("TYPE"));
    }

    private void setDataDateCb() {
        //TODO: wez zrob datepicker
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

    private void onOkClick(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Not supported yet"); //To change body of generated methods, choose Tools | Templates.
        responseTf.setText("COULD NOT GET ANY RESPONSE");
    }

    private void keyInputDispatcher() {

        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        AbstractAction escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchView.this.getWindow().dispose();
                System.exit(0);
            }
        };
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        actionMap.put("escape", escapeAction);
    }

    public MainView getWindow() {
        return window;
    }

    public void setWindow(MainView window) {
        this.window = window;
    }

    public JPanel getMainLayout() {
        return mainLayout;
    }

    public void setMainLayout(JPanel mainLayout) {
        this.mainLayout = mainLayout;
    }

    public JPanel getDataLayout() {
        return dataLayout;
    }

    public void setDataLayout(JPanel dataLayout) {
        this.dataLayout = dataLayout;
    }

    public JPanel getNavPanel() {
        return navPanel;
    }

    public void setNavPanel(JPanel navPanel) {
        this.navPanel = navPanel;
    }

    public JPanel getSearchPanel() {
        return searchPanel;
    }

    public void setSearchPanel(JPanel searchPanel) {
        this.searchPanel = searchPanel;
    }

    public JPanel getSearchLabelPanel() {
        return searchLabelPanel;
    }

    public void setSearchLabelPanel(JPanel searchLabelPanel) {
        this.searchLabelPanel = searchLabelPanel;
    }

    public JPanel getSearchDataPanel() {
        return searchDataPanel;
    }

    public void setSearchDataPanel(JPanel searchDataPanel) {
        this.searchDataPanel = searchDataPanel;
    }

    public JComboBox getDateCb() {
        return dateCb;
    }

    public void setDateCb(JComboBox dateCb) {
        this.dateCb = dateCb;
    }

    public JComboBox getTypeCb() {
        return typeCb;
    }

    public void setTypeCb(JComboBox typeCb) {
        this.typeCb = typeCb;
    }

    public JComboBox getHourStartCb() {
        return hourStartCb;
    }

    public void setHourStartCb(JComboBox hourStartCb) {
        this.hourStartCb = hourStartCb;
    }

    public JComboBox getHourStopCb() {
        return hourStopCb;
    }

    public void setHourStopCb(JComboBox hourStopCb) {
        this.hourStopCb = hourStopCb;
    }

    public JTextField getStudentsTf() {
        return studentsTf;
    }

    public void setStudentsTf(JTextField studentsTf) {
        this.studentsTf = studentsTf;
    }

    public JLabel getResponseTf() {
        return responseTf;
    }

    public void setResponseTf(JLabel responseTf) {
        this.responseTf = responseTf;
    }

    public JButton getResponseBtn() {
        return responseBtn;
    }

    public void setResponseBtn(JButton responseBtn) {
        this.responseBtn = responseBtn;
    }

}
