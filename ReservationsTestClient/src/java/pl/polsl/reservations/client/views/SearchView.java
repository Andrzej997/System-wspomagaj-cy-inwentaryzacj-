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

}
