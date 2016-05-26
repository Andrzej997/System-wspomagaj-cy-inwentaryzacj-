package pl.polsl.reservations.client.views;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import pl.polsl.reservations.client.mediators.AccountViewMediator;
import pl.polsl.reservations.client.mediators.LoginMediator;

public class LoginView extends JPanel {

    private static final long serialVersionUID = 7390610748297788567L;

    private final MainView window;

    private JButton loginButton;
    private JButton registerButton;
    private JButton guestButton;
    private JFormattedTextField loginEditText;
    private JLabel loginLabel;
    private JLabel passwordLabel;
    private JPasswordField passwordEditText;

    private LoginMediator loginMediator;

    public LoginView(MainView window, LoginMediator loginMediator) {
        super(new BorderLayout());
        initComponents();
        this.window = window;
        this.loginMediator = loginMediator;
    }

    private void initComponents() {
        initialize();

        setSize();
        initLoginFields();
        initButtons();

        JPanel loginLayout = new JPanel(new BorderLayout());

        loginLayout.add(loginLabel, BorderLayout.WEST);
        loginLayout.add(loginEditText, BorderLayout.EAST);

        JPanel passwordLayout = new JPanel(new BorderLayout());

        passwordLayout.add(passwordLabel, BorderLayout.WEST);
        passwordLayout.add(passwordEditText, BorderLayout.EAST);

        JPanel navLayout = new JPanel(new BorderLayout());
        navLayout.add(loginButton, BorderLayout.NORTH);
        navLayout.add(registerButton, BorderLayout.CENTER);
        navLayout.add(guestButton, BorderLayout.SOUTH);

        JPanel dataLayout = new JPanel(new BorderLayout());
        dataLayout.add(loginLayout, BorderLayout.NORTH);
        dataLayout.add(passwordLayout, BorderLayout.CENTER);
        dataLayout.add(navLayout, BorderLayout.SOUTH);

        JPanel mainLayout = new JPanel(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();
        mainLayout.add(dataLayout, position);
        add(mainLayout, BorderLayout.CENTER);
    }

    private void onClickLogin(java.awt.event.ActionEvent evt) {
        if (passwordEditText.getText().length() > 0 && loginEditText.getText().length() > 0) {
            window.setOptionsAvailable(Color.black);
            if(loginMediator.getUserData(loginEditText.getText(), passwordEditText.getText())){
                window.setView(new AccountViewMediator().createView(window));
                window.setLogged(true);
            } else {
                JOptionPane.showMessageDialog(this, "Wrong login or password!!.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Login and password required.");
        }
    }

    private void onClickRegister(ActionEvent evt) {

    }

    private void onClickGuest(ActionEvent evt) {
        window.setOptionsAvailable(Color.black);
        window.setView(new AccountViewMediator().createView(window));
        window.setLogged(true);
    }

    private void initialize() {
        loginEditText = new JFormattedTextField();
        passwordEditText = new JPasswordField();
        loginLabel = new JLabel();
        passwordLabel = new JLabel();
        loginButton = new JButton();
        registerButton = new JButton();
        guestButton = new JButton();
    }

    private void setSize() {
        setMaximumSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(800, 600));
        setPreferredSize(new Dimension(800, 600));
    }

    private void initLoginFields() {
        loginEditText.setToolTipText("You username");
        loginEditText.setPreferredSize(new Dimension(200, 30));
        passwordEditText.setToolTipText("Your password");
        passwordEditText.setPreferredSize(new Dimension(200, 30));

        loginLabel.setText("Login: ");
        passwordLabel.setText("Password: ");
        loginButton.setText("Login");
        loginButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            onClickLogin(evt);
        });
    }

    private void initButtons() {
        registerButton.setText("Register");
        registerButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            onClickRegister(evt);
        });
        guestButton.setText("Guest login");
        guestButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            onClickGuest(evt);
        });
    }

    public MainView getWindow() {
        return window;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JButton getGuestButton() {
        return guestButton;
    }

    public JFormattedTextField getLoginEditText() {
        return loginEditText;
    }

    public JLabel getLoginLabel() {
        return loginLabel;
    }

    public JLabel getPasswordLabel() {
        return passwordLabel;
    }

    public JPasswordField getPasswordEditText() {
        return passwordEditText;
    }

}
