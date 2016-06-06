package pl.polsl.reservations.client.views;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import pl.polsl.reservations.client.mediators.AddEditUserViewMediator;
import pl.polsl.reservations.client.views.utils.ButtonStyle;
import pl.polsl.reservations.client.views.utils.NumberFormatUtils;
import pl.polsl.reservations.client.views.utils.PanelStyle;
import pl.polsl.reservations.client.views.utils.ValidationErrorMessanger;

/**
 *
 * @author abienioszek
 */
//TODO: dodac combo z wyborem uzytkownika do edycji w widoku edycji
public class AddEditUserView extends JPanel {

    private static final long serialVersionUID = -2193290350545362057L;

    private final int NORMAL_WIDTH = 200;
    private final int NORMAL_HEIGHT = 30;

    private MainView window;
    private boolean editUser;
    private JLabel usernameLabel;
    private JLabel surnameLabel;
    private JLabel nameLabel;
    private JLabel phoneLabel;
    private JLabel emailLabel;
    private JLabel addressLabel;
    private JLabel departmentLabel;
    private JLabel gradeLabel;
    private JLabel peselLabel;

    private JTextField usernameTf;
    private JTextField nameTf;
    private JTextField surnameTf;
    private JTextField phoneTf;
    private JTextField emailTf;
    private JTextField addressTf;
    private JTextField peselTf;
    private JComboBox departmentCb;
    private JTextField gradeTf;

    private JLabel peselContentLabel;
    private JLabel departmentContentLabel;
    private JLabel usernameContentLabel;
    private JLabel gradeContentLabel;

    private JPanel labelPanel;
    private JPanel dataPanel;
    private JPanel mainPanel;

    private JButton okButton;

    private final AddEditUserViewMediator addEditUserViewMediator;

    public AddEditUserView(MainView window, boolean editUser, AddEditUserViewMediator addEditUserViewMediator) {
        super();
        this.window = window;
        this.editUser = editUser;
        this.addEditUserViewMediator = addEditUserViewMediator;
        initComponents();
        initPanels();
    }

    private void initComponents() {
        initializeObjects();
        prepareObjects();
        setupView();

    }

    private void setupView() {
        labelPanel.add(usernameLabel);
        labelPanel.add(nameLabel);
        labelPanel.add(surnameLabel);
        labelPanel.add(phoneLabel);
        labelPanel.add(emailLabel);
        labelPanel.add(addressLabel);
        labelPanel.add(departmentLabel);
        labelPanel.add(gradeLabel);
        labelPanel.add(peselLabel);

        if (editUser) {
            dataPanel.add(usernameContentLabel);
        } else {
            dataPanel.add(usernameTf);
        }
        dataPanel.add(nameTf);
        dataPanel.add(surnameTf);
        dataPanel.add(phoneTf);
        dataPanel.add(emailTf);
        dataPanel.add(addressTf);

        if (editUser) {
            dataPanel.add(departmentContentLabel);
            dataPanel.add(gradeContentLabel);
            dataPanel.add(peselContentLabel);
        } else {
            dataPanel.add(departmentCb);
            dataPanel.add(gradeTf);
            dataPanel.add(peselTf);
        }

        mainPanel.add(labelPanel);
        mainPanel.add(dataPanel);
        add(mainPanel);
        add(okButton);
    }

    private void prepareObjects() {
        try {
            Image img;
            if (editUser) {
                img = ImageIO.read(getClass().getResource("/resources/ok.png"));
            } else {
                img = ImageIO.read(getClass().getResource("/resources/add.png"));
            }
            ButtonStyle.setStyle(okButton, img);
        } catch (IOException ex) {
            System.out.println("RESOURCE ERROR: " + ex.toString());
        }

        PanelStyle.setSize(usernameLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(usernameTf, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(usernameContentLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(surnameTf, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(surnameLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(nameTf, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(nameLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(phoneTf, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(phoneLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(emailTf, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(emailLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(addressTf, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(addressLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(departmentContentLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(departmentLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(departmentCb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(peselContentLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(peselLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(peselTf, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(gradeTf, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(gradeLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(gradeContentLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(labelPanel, NORMAL_WIDTH, 240);
        PanelStyle.setSize(dataPanel, NORMAL_WIDTH, 240);
        PanelStyle.setSize(mainPanel, 2 * NORMAL_WIDTH, 300);
    }

    private void initializeObjects() {
        usernameLabel = new JLabel("Username: ");
        nameLabel = new JLabel("Name: ");
        surnameLabel = new JLabel("Surname: ");
        phoneLabel = new JLabel("Phone: ");
        emailLabel = new JLabel("Email: ");
        addressLabel = new JLabel("Address: ");
        departmentLabel = new JLabel("Department: ");
        gradeLabel = new JLabel("Grade: ");
        peselLabel = new JLabel("Pesel: ");

        usernameContentLabel = new JLabel();
        departmentContentLabel = new JLabel();
        gradeContentLabel = new JLabel();
        peselContentLabel = new JLabel();

        usernameTf = new JTextField();
        nameTf = new JTextField();
        surnameTf = new JTextField();
        phoneTf = new JTextField();
        emailTf = new JTextField();
        addressTf = new JTextField();
        departmentCb = new JComboBox();
        gradeTf = new JTextField();
        peselTf = new JTextField();

        okButton = new JButton();
        okButton.addActionListener((ActionEvent e) -> {
            if (!validateFields()) {
                return;
            }
            if (editUser) {
                addEditUserViewMediator.onChangeUserData();
            } else {
                addEditUserViewMediator.onAddUser();
            }
        });
        mainPanel = new JPanel(new GridLayout(1, 2));
        dataPanel = new JPanel();
        labelPanel = new JPanel();
        keyInputDispatcher();
    }

    private void initPanels() {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
    }

    private void keyInputDispatcher() {

        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        AbstractAction escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEditUserView.this.getWindow().dispose();
                System.exit(0);
            }
        };
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        actionMap.put("escape", escapeAction);
    }

    private Boolean validateFields() {
        Boolean validationFlag = true;
        if (editUser) {
            if (NumberFormatUtils.isInteger(phoneTf.getText())) {
                ValidationErrorMessanger.showErrorMessage(phoneTf, "Phone is not a number");
                validationFlag = false;
            }
            if (addressTf.getText().isEmpty()) {
                ValidationErrorMessanger.showErrorMessage(addressTf, "Address field cannot be empty");
                validationFlag = false;
            }
            if (emailTf.getText().isEmpty()) {
                ValidationErrorMessanger.showErrorMessage(emailTf, "Email field cannot be empty");
                validationFlag = false;
            }
            if (nameTf.getText().isEmpty()) {
                ValidationErrorMessanger.showErrorMessage(nameTf, "Name field cannot be empty");
                validationFlag = false;
            }
            if (phoneTf.getText().isEmpty()) {
                ValidationErrorMessanger.showErrorMessage(phoneTf, "Phone field cannot be empty");
                validationFlag = false;
            }
            if (surnameTf.getText().isEmpty()) {
                ValidationErrorMessanger.showErrorMessage(surnameTf, "Surname field cannot be empty");
                validationFlag = false;
            }

        } else {
            if (addressTf.getText().isEmpty()) {
                ValidationErrorMessanger.showErrorMessage(addressTf, "Address field cannot be empty");
                validationFlag = false;
            }
            if (emailTf.getText().isEmpty()) {
                ValidationErrorMessanger.showErrorMessage(emailTf, "Email field cannot be empty");
                validationFlag = false;
            }
            if (nameTf.getText().isEmpty()) {
                ValidationErrorMessanger.showErrorMessage(nameTf, "Name field cannot be empty");
                validationFlag = false;
            }
            if (phoneTf.getText().isEmpty()) {
                ValidationErrorMessanger.showErrorMessage(peselTf, "Phone field cannot be empty");
                validationFlag = false;
            }
            if (NumberFormatUtils.isInteger(phoneTf.getText())) {
                ValidationErrorMessanger.showErrorMessage(phoneTf, "Phone is not a number");
                validationFlag = false;
            }
            if (surnameTf.getText().isEmpty()) {
                ValidationErrorMessanger.showErrorMessage(surnameTf, "Surname field cannot be empty");
                validationFlag = false;
            }
            if (gradeTf.getText().isEmpty()) {
                ValidationErrorMessanger.showErrorMessage(gradeTf, "Grade field cannot be empty");
                validationFlag = false;
            }
            if (peselTf.getText().isEmpty()) {
                ValidationErrorMessanger.showErrorMessage(peselTf, "Pesel field cannot be empty");
                validationFlag = false;
            }
            if (usernameTf.getText().isEmpty()) {
                ValidationErrorMessanger.showErrorMessage(usernameTf, "Username field cannot be empty");
                validationFlag = false;
            }
        }
        return validationFlag;
    }

    /**
     * @return the editUser
     */
    public boolean isEditUser() {
        return editUser;
    }

    /**
     * @param editUser the editUser to set
     */
    public void setEditUser(boolean editUser) {
        this.editUser = editUser;
    }

    /**
     * @return the usernameLabel
     */
    public JLabel getUsernameLabel() {
        return usernameLabel;
    }

    /**
     * @param usernameLabel the usernameLabel to set
     */
    public void setUsernameLabel(JLabel usernameLabel) {
        this.usernameLabel = usernameLabel;
    }

    /**
     * @return the surnameLabel
     */
    public JLabel getSurnameLabel() {
        return surnameLabel;
    }

    /**
     * @param surnameLabel the surnameLabel to set
     */
    public void setSurnameLabel(JLabel surnameLabel) {
        this.surnameLabel = surnameLabel;
    }

    /**
     * @return the nameLabel
     */
    public JLabel getNameLabel() {
        return nameLabel;
    }

    /**
     * @param nameLabel the nameLabel to set
     */
    public void setNameLabel(JLabel nameLabel) {
        this.nameLabel = nameLabel;
    }

    /**
     * @return the phoneLabel
     */
    public JLabel getPhoneLabel() {
        return phoneLabel;
    }

    /**
     * @param phoneLabel the phoneLabel to set
     */
    public void setPhoneLabel(JLabel phoneLabel) {
        this.phoneLabel = phoneLabel;
    }

    /**
     * @return the emailLabel
     */
    public JLabel getEmailLabel() {
        return emailLabel;
    }

    /**
     * @param emailLabel the emailLabel to set
     */
    public void setEmailLabel(JLabel emailLabel) {
        this.emailLabel = emailLabel;
    }

    /**
     * @return the addressLabel
     */
    public JLabel getAddressLabel() {
        return addressLabel;
    }

    /**
     * @param addressLabel the addressLabel to set
     */
    public void setAddressLabel(JLabel addressLabel) {
        this.addressLabel = addressLabel;
    }

    /**
     * @return the departmentLabel
     */
    public JLabel getDepartmentLabel() {
        return departmentLabel;
    }

    /**
     * @param departmentLabel the departmentLabel to set
     */
    public void setDepartmentLabel(JLabel departmentLabel) {
        this.departmentLabel = departmentLabel;
    }

    /**
     * @return the gradeLabel
     */
    public JLabel getGradeLabel() {
        return gradeLabel;
    }

    /**
     * @param gradeLabel the gradeLabel to set
     */
    public void setGradeLabel(JLabel gradeLabel) {
        this.gradeLabel = gradeLabel;
    }

    /**
     * @return the peselLabel
     */
    public JLabel getPeselLabel() {
        return peselLabel;
    }

    /**
     * @param peselLabel the peselLabel to set
     */
    public void setPeselLabel(JLabel peselLabel) {
        this.peselLabel = peselLabel;
    }

    /**
     * @return the usernameTf
     */
    public JTextField getUsernameTf() {
        return usernameTf;
    }

    /**
     * @param usernameTf the usernameTf to set
     */
    public void setUsernameTf(JTextField usernameTf) {
        this.usernameTf = usernameTf;
    }

    /**
     * @return the nameTf
     */
    public JTextField getNameTf() {
        return nameTf;
    }

    /**
     * @param nameTf the nameTf to set
     */
    public void setNameTf(JTextField nameTf) {
        this.nameTf = nameTf;
    }

    /**
     * @return the surnameTf
     */
    public JTextField getSurnameTf() {
        return surnameTf;
    }

    /**
     * @param surnameTf the surnameTf to set
     */
    public void setSurnameTf(JTextField surnameTf) {
        this.surnameTf = surnameTf;
    }

    /**
     * @return the phoneTf
     */
    public JTextField getPhoneTf() {
        return phoneTf;
    }

    /**
     * @param phoneTf the phoneTf to set
     */
    public void setPhoneTf(JTextField phoneTf) {
        this.phoneTf = phoneTf;
    }

    /**
     * @return the emailTf
     */
    public JTextField getEmailTf() {
        return emailTf;
    }

    /**
     * @param emailTf the emailTf to set
     */
    public void setEmailTf(JTextField emailTf) {
        this.emailTf = emailTf;
    }

    /**
     * @return the addressTf
     */
    public JTextField getAddressTf() {
        return addressTf;
    }

    /**
     * @param addressTf the addressTf to set
     */
    public void setAddressTf(JTextField addressTf) {
        this.addressTf = addressTf;
    }

    /**
     * @return the peselTf
     */
    public JTextField getPeselTf() {
        return peselTf;
    }

    /**
     * @param peselTf the peselTf to set
     */
    public void setPeselTf(JTextField peselTf) {
        this.peselTf = peselTf;
    }

    /**
     * @return the departmentCb
     */
    public JComboBox getDepartmentCb() {
        return departmentCb;
    }

    /**
     * @param departmentCb the departmentCb to set
     */
    public void setDepartmentCb(JComboBox departmentCb) {
        this.departmentCb = departmentCb;
    }

    /**
     * @return the gradeTf
     */
    public JTextField getGradeTf() {
        return gradeTf;
    }

    /**
     * @param gradeTf the gradeTf to set
     */
    public void setGradeTf(JTextField gradeTf) {
        this.gradeTf = gradeTf;
    }

    /**
     * @return the peselContentLabel
     */
    public JLabel getPeselContentLabel() {
        return peselContentLabel;
    }

    /**
     * @param peselContentLabel the peselContentLabel to set
     */
    public void setPeselContentLabel(JLabel peselContentLabel) {
        this.peselContentLabel = peselContentLabel;
    }

    /**
     * @return the departmentContentLabel
     */
    public JLabel getDepartmentContentLabel() {
        return departmentContentLabel;
    }

    /**
     * @param departmentContentLabel the departmentContentLabel to set
     */
    public void setDepartmentContentLabel(JLabel departmentContentLabel) {
        this.departmentContentLabel = departmentContentLabel;
    }

    /**
     * @return the usernameContentLabel
     */
    public JLabel getUsernameContentLabel() {
        return usernameContentLabel;
    }

    /**
     * @param usernameContentLabel the usernameContentLabel to set
     */
    public void setUsernameContentLabel(JLabel usernameContentLabel) {
        this.usernameContentLabel = usernameContentLabel;
    }

    /**
     * @return the gradeContentLabel
     */
    public JLabel getGradeContentLabel() {
        return gradeContentLabel;
    }

    /**
     * @param gradeContentLabel the gradeContentLabel to set
     */
    public void setGradeContentLabel(JLabel gradeContentLabel) {
        this.gradeContentLabel = gradeContentLabel;
    }

    /**
     * @return the labelPanel
     */
    public JPanel getLabelPanel() {
        return labelPanel;
    }

    /**
     * @param labelPanel the labelPanel to set
     */
    public void setLabelPanel(JPanel labelPanel) {
        this.labelPanel = labelPanel;
    }

    /**
     * @return the dataPanel
     */
    public JPanel getDataPanel() {
        return dataPanel;
    }

    /**
     * @param dataPanel the dataPanel to set
     */
    public void setDataPanel(JPanel dataPanel) {
        this.dataPanel = dataPanel;
    }

    /**
     * @return the mainPanel
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * @param mainPanel the mainPanel to set
     */
    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    /**
     * @return the okButton
     */
    public JButton getOkButton() {
        return okButton;
    }

    /**
     * @param okButton the okButton to set
     */
    public void setOkButton(JButton okButton) {
        this.okButton = okButton;
    }

    public MainView getWindow() {
        return window;
    }

    public void setWindow(MainView window) {
        this.window = window;
    }
}
