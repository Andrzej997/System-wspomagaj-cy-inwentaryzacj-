/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.reservations.client.views;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.*;

public class AccountView extends JPanel {

    private MainWindow window;
    private JComboBox chooseRoomDropdown;
    private JButton passwordButton;
    private JButton addButton;
    private JButton chooseButton;

    public AccountView(MainWindow window) {
        super(new BorderLayout());
        initComponents();
        this.window = window;
    }

    private void initComponents() {
        initFields();
        setSize();
        initRoomDropdown();
        initButtons();

        //TODO - rzeczywiste sale
        JPanel planLayout = new JPanel(new BorderLayout());
        planLayout.add(chooseRoomDropdown, BorderLayout.WEST);
        planLayout.add(chooseButton, BorderLayout.EAST);

        JPanel dataLayout = new JPanel(new BorderLayout());
        dataLayout.add(planLayout, BorderLayout.NORTH);
        dataLayout.add(passwordButton, BorderLayout.CENTER);
        dataLayout.add(addButton, BorderLayout.SOUTH);

        JPanel mainLayout = new JPanel(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();
        mainLayout.add(dataLayout, position);
        add(mainLayout, BorderLayout.CENTER);

    }

    private void onRoomClick(java.awt.event.ActionEvent evt) {
        window.setView(new WeekDataView(window, chooseRoomDropdown.getSelectedItem()));
        //TODO - get data about room
    }

    private void onChangePasswordClick(java.awt.event.ActionEvent evt) {
        String password = JOptionPane.showInputDialog("Type new password");
        String passwordConfirm = JOptionPane.showInputDialog("Confirm password");
        if (!password.equals(passwordConfirm)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.");
        }
        //TODO - change password
    }

    private void onAddUser(java.awt.event.ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Not supported yet");
        //TODO - add user
    }

    private void initFields() {

        chooseRoomDropdown = new JComboBox();
        passwordButton = new JButton();
        addButton = new JButton();
        chooseButton = new JButton();
    }

    private void setSize() {

        setMaximumSize(new java.awt.Dimension(800, 600));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));
    }

    private void initRoomDropdown() {
        chooseRoomDropdown.addItem("1");
        chooseRoomDropdown.addItem("2");
        chooseRoomDropdown.setToolTipText("Choose room");
    }

    private void initButtons() {
        chooseButton.setText("Show plan");
        chooseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onRoomClick(evt);
            }
        });

        passwordButton.setText("Change password");
        passwordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onChangePasswordClick(evt);
            }
        });

        addButton.setText("Add new user");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onAddUser(evt);
            }
        });
    }
}
