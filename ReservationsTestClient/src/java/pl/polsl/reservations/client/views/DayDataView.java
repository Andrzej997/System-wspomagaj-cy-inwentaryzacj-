/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Ola
 */
public class DayDataView extends javax.swing.JPanel {

    MainWindow window;
    Object date;

    private JComboBox chooseRoomDropdown;
    private JButton chooseButton;
    private JButton nextWeek;
    private JButton prevWeek;
    private JButton backButton;
    private JTable planView;
    private JLabel weekTv;

    public DayDataView(MainWindow window, Object i) {
        initComponents();
        this.window = window;
        this.date = i;
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
        chooseRoomDropdown.addItem("1");
        chooseRoomDropdown.addItem("2");
    }

    private void fillTable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void initButtons() {
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onBackClick(evt);
            }
        });

        //TODO - logika zmiany dnia 
        nextWeek.setText("NEXT DAY");
        prevWeek.setText("PREV DAY");
        prevWeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onPrevClick(evt);
            }
        });
        nextWeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onNextClick(evt);
            }
        });
        backButton.setText("BACK");
        weekTv.setText("DATA");

        chooseButton.setText("OK");
        chooseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onOkClick(evt);
            }
        });
        chooseButton.setPreferredSize(new Dimension(200, 30));
    }

    private void setSize() {
        setMaximumSize(new java.awt.Dimension(800, 600));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));
    }

    private class ButtonColumnListener implements ActionListener {

        private int index;

        public ButtonColumnListener(int i) {
            index = i + 1;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            window.setView(new DayDataView(window, index));
        }
    }

    private void onBackClick(ActionEvent evt) {
        window.setView(new WeekDataView(window, chooseRoomDropdown.getSelectedItem()));
    }

    private void onNextClick(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Not supported yet"); //To change body of generated methods, choose Tools | Templates.
    }

    private void onPrevClick(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Not supported yet"); //To change body of generated methods, choose Tools | Templates.
    }

    private void onOkClick(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Not supported yet"); //To change body of generated methods, choose Tools | Templates.
    }
}
