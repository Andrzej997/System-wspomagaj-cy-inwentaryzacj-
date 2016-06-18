package pl.polsl.reservations.client.views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import pl.polsl.reservations.client.ClientContext;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.mediators.LoginMediator;
import pl.polsl.reservations.client.mediators.WeekDataViewMediator;
import pl.polsl.reservations.client.views.utils.ButtonStyle;
import pl.polsl.reservations.client.views.utils.MessageBoxUtils;
import pl.polsl.reservations.client.views.utils.PanelStyle;
import pl.polsl.reservations.client.views.utils.ValidationErrorMessanger;

public class LoginView extends JPanel {

    private final int NORMAL_WIDTH = 130;
    private final int NORMAL_HEIGHT = 30;
    private static final long serialVersionUID = 7390610748297788567L;

    private final MainView window;

    private JButton loginButton;
    private JButton guestButton;
    private JTextField loginTf;
    private JLabel loginLabel;
    private JLabel passwordLabel;
    private JPasswordField passwordTf;

    private final transient LoginMediator loginMediator;

    public LoginView(MainView window, LoginMediator loginMediator) {
        super(new BorderLayout());
        initComponents();
        this.window = window;
        this.loginMediator = loginMediator;
    }

    private void initComponents() {
        initialize();
        PanelStyle.setSize(this, 300, 200);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        initLoginFields();
        initButtons();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        labelPanel.add(loginLabel);
        dataPanel.add(loginTf);
        labelPanel.add(passwordLabel);
        dataPanel.add(passwordTf);

        PanelStyle.setSize(loginLabel, NORMAL_WIDTH, NORMAL_HEIGHT);

        PanelStyle.setSize(passwordLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(loginTf, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(passwordTf, NORMAL_WIDTH, NORMAL_HEIGHT);

        mainPanel.add(labelPanel);
        mainPanel.add(dataPanel);
        add(mainPanel);
        add(loginButton);
        add(guestButton);
        setupListeners();
        keyInputDispatcher();
    }

    private void onClickLogin(java.awt.event.ActionEvent evt) {
        if (!validateAll()) {
            return;
        }
        if (loginMediator.getUserData(loginTf.getText(), passwordTf.getText())) {
            window.setOptionsAvailable(Color.black);
            ClientContext.getInstance().setUsername(loginTf.getText());
            window.setLogged(true);
            window.setView(new WeekDataViewMediator().createView(window, loginMediator.getFirstRoom()));
            MessageBoxUtils.createAvaliableRequestsMessage(this);
        } else {
            JOptionPane.showMessageDialog(this, "Wrong login or password!!.");
        }
    }

    private void onClickGuest(ActionEvent evt) {
        loginMediator.loginAsGuest();
        window.setOptionsAvailable(Color.black);
        window.setLogged(true);
        window.setView(new WeekDataViewMediator().createView(window, loginMediator.getFirstRoom()));
    }

    private void initialize() {
        loginTf = new JFormattedTextField();
        passwordTf = new JPasswordField();
        loginLabel = new JLabel();
        passwordLabel = new JLabel();
        loginButton = new JButton();
        guestButton = new JButton();
    }

    private void initLoginFields() {

        loginLabel.setText("Login: ");
        passwordLabel.setText("Password: ");
        try {
            Image img = ImageIO.read(getClass().getResource("/resources/login.png"));
            ButtonStyle.setStyle(loginButton, img);
        } catch (IOException ex) {
            System.out.println("RESOURCE ERROR: " + ex.toString());
        }
    }

    private void initButtons() {

        try {
            Image img = ImageIO.read(getClass().getResource("/resources/guest_login.png"));
            ButtonStyle.setStyle(guestButton, img);
        } catch (IOException ex) {
            System.out.println("RESOURCE ERROR: " + ex.toString());
        }
    }

    private void setupListeners() {
        guestButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            onClickGuest(evt);
        });
        loginButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            if (!validateAll()) {
                return;
            }
            onClickLogin(evt);
        });
    }

    private void keyInputDispatcher() {

        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        AbstractAction loginAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginView.this.onClickLogin(e);
            }
        };
        AbstractAction guestLoginAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginView.this.onClickGuest(e);
            }
        };
        AbstractAction escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginView.this.getWindow().dispose();
                Lookup.removeUserCertificate();
                System.exit(0);
            }
        };
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "login");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "guestLogin");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        actionMap.put("login", loginAction);
        actionMap.put("guestLogin", guestLoginAction);
        actionMap.put("escape", escapeAction);
    }

    private Boolean validateAll() {
        Boolean validationFlag = true;
        if (loginTf.getText().isEmpty()) {
            ValidationErrorMessanger.showErrorMessage(loginTf, "Username field cannot be empty");
            validationFlag = false;
        }
        return validationFlag;
    }

    public MainView getWindow() {
        return window;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getGuestButton() {
        return guestButton;
    }

    public JTextField getLoginEditText() {
        return loginTf;
    }

    public JLabel getLoginLabel() {
        return loginLabel;
    }

    public JLabel getPasswordLabel() {
        return passwordLabel;
    }

    public JPasswordField getPasswordEditText() {
        return passwordTf;
    }

}
