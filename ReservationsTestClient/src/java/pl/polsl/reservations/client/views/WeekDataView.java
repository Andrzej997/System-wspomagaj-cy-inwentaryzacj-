/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.reservations.client.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import pl.polsl.reservations.client.mediators.DayDataViewMediator;
import pl.polsl.reservations.client.mediators.WeekDataViewMediator;

/**
 *
 * @author Ola
 */
public class WeekDataView extends JPanel {

    MainView window;
    private JComboBox chooseRoomDropdown;
    private JButton chooseButton;
    private JButton nextWeek;
    private JButton prevWeek;
    private JTable planView;
    private JPanel buttonPanel;
    private JLabel weekTv;
    
    private WeekDataViewMediator weekDataViewMediator;

    public WeekDataView(MainView window, Object selectedItem,WeekDataViewMediator weekDataViewMediator) {
        this.window = window;   
        this.weekDataViewMediator = weekDataViewMediator;
        initComponents();
        chooseRoomDropdown.setSelectedItem(selectedItem);
    }

    private void initComponents() {
        chooseRoomDropdown = new JComboBox();
        chooseButton = new JButton();
        //TODO - dodaj logikę pobierania numerów pokoi
        chooseRoomDropdown.addItem("1");
        chooseRoomDropdown.addItem("2");
        nextWeek = new JButton();
        prevWeek = new JButton();
        planView = new JTable();
        buttonPanel = new JPanel(new GridLayout(1,8));
        weekTv = new JLabel();
        
        
         //TODO - logika zmiany tygodnia
        nextWeek.setText("NEXT WEEK");
        prevWeek.setText("PREV WEEK");
        weekTv.setText("POCZATEK DATA - KONIEC DATA");
        chooseButton.setText("OK");
        chooseButton.setPreferredSize(new Dimension(200,30));
        initTable();
        initHeaders();

        setMaximumSize(new java.awt.Dimension(800, 600));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));
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
        dataLayout.add(buttonPanel, BorderLayout.CENTER);
        dataLayout.add(planView, BorderLayout.SOUTH);

        JPanel mainLayout = new JPanel(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();
        mainLayout.add(dataLayout, position);
        add(mainLayout, BorderLayout.CENTER);

    }

    private void initTable() {
        TableModel dataModel = new DefaultTableModel() {
            @Override
            public int getColumnCount() {
                return 7;
            }

            @Override
            public int getRowCount() {
                return 32;
            }

            @Override
            public Object getValueAt(int row, int column) {
                return row;
            }

        };
        planView = new JTable(dataModel);
    }

    private void initHeaders() {
        for (int i = 0; i < 7; i++) {
           
            JButton temp = new JButton(String.valueOf(i + 1));
            temp.addActionListener(new ButtonColumnListener(i));
            temp.setPreferredSize(new Dimension(40, 40));
            buttonPanel.add(temp);
        }
        buttonPanel.setPreferredSize(new Dimension(800, 40));
    }

    private class ButtonColumnListener implements ActionListener {

        private int index;

        public ButtonColumnListener(int i) {
            index = i + 1;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            window.setView(new DayDataViewMediator().createView(window, index));
        }
    }

    public void setPlanView(JTable planView) {
        this.planView = planView;
    }

    public MainView getWindow() {
        return window;
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

    public JTable getPlanView() {
        return planView;
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public JLabel getWeekTv() {
        return weekTv;
    }
    
}
