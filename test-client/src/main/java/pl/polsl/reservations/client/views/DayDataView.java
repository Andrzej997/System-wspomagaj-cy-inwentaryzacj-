package pl.polsl.reservations.client.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import pl.polsl.reservations.client.mediators.DayDataViewMediator;
import pl.polsl.reservations.client.mediators.WeekDataViewMediator;

/**
 *
 * @author Ola
 */
public class DayDataView extends javax.swing.JPanel {

    private static final long serialVersionUID = 649020928680112340L;

    MainView window;
    Object date;

    private JComboBox chooseRoomDropdown;
    private JButton chooseButton;
    private JButton nextWeek;
    private JButton prevWeek;
    private JButton backButton;
    private JTable planView;
    private JLabel weekTv;

    private final transient DayDataViewMediator dayDataViewMediator;

    public DayDataView(MainView window, Object i, DayDataViewMediator dayDataViewMediator) {
        this.window = window;
        this.date = i;
        this.dayDataViewMediator = dayDataViewMediator;
        initComponents();

    }

    private void initComponents() {
        initFields();
        initRoomDropdown();
        initButtons();
        planView = new JTable(new DayTableModel());

        setSize();
        JPanel weekPanel = new JPanel(new BorderLayout());
        weekPanel.add(prevWeek, BorderLayout.WEST);
        weekPanel.add(weekTv, BorderLayout.CENTER);
        weekPanel.add(nextWeek, BorderLayout.EAST);
        JPanel navPanel = new JPanel(new BorderLayout());
        navPanel.add(chooseRoomDropdown, BorderLayout.WEST);
        navPanel.add(chooseButton, BorderLayout.CENTER);
        navPanel.add(weekPanel, BorderLayout.EAST);
        JPanel dataLayout = new JPanel(new BorderLayout());
        dataLayout.add(navPanel, BorderLayout.NORTH);
        dataLayout.add(backButton, BorderLayout.CENTER);
        dataLayout.add(planView, BorderLayout.SOUTH);

        JPanel mainLayout = new JPanel(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();
        mainLayout.add(dataLayout, position);
        add(mainLayout, BorderLayout.CENTER);

    }

    private void initFields() {
        chooseRoomDropdown = new JComboBox();
        chooseButton = new JButton();
        //TODO - dodaj logikę pobierania numerów pokoi

        nextWeek = new JButton();
        prevWeek = new JButton();
        planView = new JTable();
        weekTv = new JLabel();
        backButton = new JButton();
    }

    private void initRoomDropdown() {
    }

    private void fillTable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void initButtons() {
        backButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            onBackClick(evt);
        });

        //TODO - logika zmiany dnia 
        nextWeek.setText("NEXT DAY");
        prevWeek.setText("PREV DAY");
        prevWeek.addActionListener((java.awt.event.ActionEvent evt) -> {
            onPrevClick(evt);
        });
        nextWeek.addActionListener((java.awt.event.ActionEvent evt) -> {
            onNextClick(evt);
        });
        backButton.setText("BACK");
        weekTv.setText("DATA");

        chooseButton.setText("OK");
        chooseButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            onOkClick(evt);
        });
        chooseButton.setPreferredSize(new Dimension(200, 30));
    }

    private void setSize() {
        setMaximumSize(new java.awt.Dimension(800, 600));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));
    }

    private class ButtonColumnListener implements ActionListener {

        private final int index;

        public ButtonColumnListener(int i) {
            index = i + 1;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //  window.setView(new DayDataView(window, index));
            window.setView(new DayDataViewMediator().createView(window, index));
        }
    }

    public MainView getWindow() {
        return window;
    }

    public Object getDate() {
        return date;
    }

    public JComboBox getChooseRoomDropdown() {
        return chooseRoomDropdown;
    }

    public JButton getChooseButton() {
        return chooseButton;
    }

    public JButton getNextWeek() {
        return nextWeek;
    }

    public JButton getPrevWeek() {
        return prevWeek;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JTable getPlanView() {
        return planView;
    }

    public JLabel getWeekTv() {
        return weekTv;
    }

    private void onBackClick(ActionEvent evt) {
        window.setView(new WeekDataViewMediator().createView(window, chooseRoomDropdown.getSelectedItem()));
    }

    private void onNextClick(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Not supported yet"); //To change body of generated methods, choose Tools | Templates.
    }

    private void onPrevClick(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Not supported yet"); //To change body of generated methods, choose Tools | Templates.
    }

    private void onOkClick(ActionEvent evt) {
        if (chooseRoomDropdown.getSelectedItem() != null) {
            dayDataViewMediator.getReservations();
        }
    }
}
