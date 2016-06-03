package pl.polsl.reservations.client.views;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 *
 * @author Ola
 */
public class SearchView extends JPanel {

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

    SearchView(MainView window) {
        super(new BorderLayout());
        this.window = window;
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
    }

    private void initSearchDataPanel() {
        setDataDateCb();
        setDataHourCb();
        setDataTypeCb();
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
        //TODO: rzeczywiste dane
        dateCb.addItem("111");
        dateCb.addItem("111");
        dateCb.addItem("111");
        dateCb.addItem("111");
    }

    private void setDataTypeCb() {
        //TODO: rzeczywiste dane
        typeCb.addItem("111");
        typeCb.addItem("111");
        typeCb.addItem("111");
        typeCb.addItem("111");
    }

    private void setDataHourCb() {
        //TODO: rzeczywiste dane
        hourStartCb.addItem("111");
        hourStartCb.addItem("111");
        hourStopCb.addItem("111");
        hourStopCb.addItem("111");
    }

    private void onOkClick(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Not supported yet"); //To change body of generated methods, choose Tools | Templates.
        responseTf.setText("COULD NOT GET ANY RESPONSE");
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
