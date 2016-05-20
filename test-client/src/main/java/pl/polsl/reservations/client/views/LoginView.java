package pl.polsl.reservations.client.views;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

class LoginView extends JPanel {

    private final MainWindow window;

    private JButton loginButton;
    private JButton registerButton;
    private JButton guestButton;
    private JFormattedTextField loginEditText;
    private JLabel loginLabel;
    private JLabel passwordLabel;
    private JPasswordField passwordEditText;

    public LoginView(MainWindow window) {
        super(new BorderLayout());
        initComponents();
        this.window = window;
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
            window.setView(new AccountView(window));
            window.setLogged(true);
        } else {
            JOptionPane.showMessageDialog(this, "Login and password required.");
        }
    }

    private void onClickRegister(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Not supported yet");
    }

    private void onClickGuest(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Not supported yet");
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
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickLogin(evt);
            }
        });
    }

    private void initButtons() {
        registerButton.setText("Register");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickRegister(evt);
            }
        });
        guestButton.setText("Guest login");
        guestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickGuest(evt);
            }
        });
    }

}
