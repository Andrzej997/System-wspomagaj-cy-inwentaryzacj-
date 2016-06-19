package pl.polsl.reservations.client.views;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import javax.swing.*;
import pl.polsl.reservations.client.mediators.AccountViewMediator;

public class AccountView extends JPanel {

    private static final long serialVersionUID = -5623275624620386899L;

    private final MainView window;
    private JComboBox chooseRoomDropdown;
    private JButton passwordButton;
    private JButton addButton;
    private JButton chooseButton;

    private transient final AccountViewMediator accountViewMediator;

    public AccountView(MainView window, AccountViewMediator accountViewMediator) {
        super(new BorderLayout());
        this.accountViewMediator = accountViewMediator;
        initComponents();
        this.window = window;
    }

    private void initComponents() {
        initFields();
        setSize();
        initRoomDropdown();
        initButtons();

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

    private void onRoomClick(ActionEvent evt) {
        accountViewMediator.dispatchRoomClickEvent(evt);
    }

    private void onChangePasswordClick(java.awt.event.ActionEvent evt) {
        accountViewMediator.dispatchChangePasswordClickEvent(evt);
    }

    private void onAddUser(java.awt.event.ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Not supported yet");
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
        chooseRoomDropdown.setToolTipText("Choose room");
    }

    private void initButtons() {
        chooseButton.setText("Show plan");
        chooseButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            onRoomClick(evt);
        });

        passwordButton.setText("Change password");
        passwordButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            onChangePasswordClick(evt);
        });

        addButton.setText("Add new user");
        addButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            onAddUser(evt);
        });
    }

    public JComboBox getChooseRoomDropdown() {
        return chooseRoomDropdown;
    }

    public JButton getPasswordButton() {
        return passwordButton;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getChooseButton() {
        return chooseButton;
    }

    public MainView getWindow() {
        return window;
    }
}
