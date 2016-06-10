package pl.polsl.reservations.client.views;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
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
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.mediators.AddEditUserViewMediator;
import pl.polsl.reservations.client.views.utils.AddUserEnum;
import pl.polsl.reservations.client.views.utils.ButtonStyle;
import pl.polsl.reservations.client.views.utils.NumberFormatUtils;
import pl.polsl.reservations.client.views.utils.PanelStyle;
import pl.polsl.reservations.client.views.utils.RoomComboBox;
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
    private AddUserEnum option;
    private JLabel usernameLabel;
    private JLabel surnameLabel;
    private JLabel nameLabel;
    private JLabel phoneLabel;
    private JLabel emailLabel;
    private JLabel addressLabel;
    private JLabel departmentLabel;
    private JLabel gradeLabel;
    private JLabel peselLabel;
    private JLabel roomLabel;
    private JLabel permissionLabel;
    private JLabel chiefLabel;

    private JTextField usernameTf;
    private JTextField nameTf;
    private JTextField surnameTf;
    private JTextField phoneTf;
    private JTextField emailTf;
    private JTextField addressTf;
    private JTextField peselTf;
    private JComboBox departmentCb;
    private RoomComboBox roomCb;
    private JComboBox permissionCb;
    private JComboBox userCb;
    private JTextField gradeTf;
    private JComboBox chiefCb;

    private JLabel peselContentLabel;
    private JLabel departmentContentLabel;
    private JLabel usernameContentLabel;
    private JLabel gradeContentLabel;
    private JLabel permissionContentLabel;
    private JLabel roomContentLabel;
    private JLabel chiefContentLabel;

    private JPanel labelPanel;
    private JPanel dataPanel;
    private JPanel mainPanel;

    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;

    private final AddEditUserViewMediator addEditUserViewMediator;

    public AddEditUserView(MainView window, AddUserEnum option, AddEditUserViewMediator addEditUserViewMediator) {
        super();
        this.window = window;
        this.option = option;
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
        labelPanel.add(roomLabel);
        labelPanel.add(permissionLabel);
        labelPanel.add(peselLabel);
        labelPanel.add(chiefLabel);

        if (option == AddUserEnum.EDIT) {
            dataPanel.add(usernameContentLabel);
        } else {
            dataPanel.add(usernameTf);
        }
        dataPanel.add(nameTf);
        dataPanel.add(surnameTf);
        dataPanel.add(phoneTf);
        dataPanel.add(emailTf);
        dataPanel.add(addressTf);

        if (option == AddUserEnum.EDIT) {
            dataPanel.add(departmentContentLabel);
            dataPanel.add(gradeContentLabel);
            dataPanel.add(roomContentLabel);
            dataPanel.add(permissionContentLabel);
            dataPanel.add(peselContentLabel);
            dataPanel.add(chiefContentLabel);
        } else {
            dataPanel.add(departmentCb);
            dataPanel.add(gradeTf);
            dataPanel.add(roomCb);
            dataPanel.add(permissionCb);
            dataPanel.add(peselTf);
            dataPanel.add(chiefCb);
        }
        if (option == AddUserEnum.ADMIN) {
            add(userCb);
        }
        mainPanel.add(labelPanel);
        mainPanel.add(dataPanel);
        add(mainPanel);
        if (null != option) {
            switch (option) {
                case EDIT:
                    add(editButton);
                    break;
                case ADD:
                    add(addButton);
                    break;
                default:
                    JPanel buttonPanel = new JPanel();
                    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
                    buttonPanel.add(editButton);
                    buttonPanel.add(deleteButton);
                    add(buttonPanel);
                    break;
            }
        }
    }

    private void prepareObjects() {
        try {
            Image img = ImageIO.read(getClass().getResource("/resources/add.png"));
            ButtonStyle.setStyle(addButton, img);
            Image img2 = ImageIO.read(getClass().getResource("/resources/ok.png"));
            ButtonStyle.setStyle(editButton, img2);
            Image img3 = ImageIO.read(getClass().getResource("/resources/error.png"));
            ButtonStyle.setStyle(deleteButton, img3);
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
        PanelStyle.setSize(roomContentLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(roomLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(roomCb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(permissionContentLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(permissionLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(permissionCb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(chiefContentLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(chiefLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(chiefCb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(userCb, 2 * NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(labelPanel, NORMAL_WIDTH, 270);
        PanelStyle.setSize(dataPanel, NORMAL_WIDTH, 270);
        PanelStyle.setSize(mainPanel, 2 * NORMAL_WIDTH, 330);
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
        permissionLabel = new JLabel("Permission: ");
        chiefLabel = new JLabel("Chief: ");

        usernameContentLabel = new JLabel();
        departmentContentLabel = new JLabel();
        gradeContentLabel = new JLabel();
        peselContentLabel = new JLabel();
        permissionContentLabel = new JLabel();
        chiefContentLabel = new JLabel();

        usernameTf = new JTextField();
        nameTf = new JTextField();
        surnameTf = new JTextField();
        phoneTf = new JTextField();
        emailTf = new JTextField();
        addressTf = new JTextField();
        departmentCb = new JComboBox();
        gradeTf = new JTextField();
        peselTf = new JTextField();
        permissionCb = new JComboBox();
        roomLabel = new JLabel("Room");
        roomContentLabel = new JLabel();
        roomCb = new RoomComboBox();
        userCb = new JComboBox();
        chiefCb = new JComboBox();

        addButton = new JButton();
        addButton.addActionListener((ActionEvent e) -> {
            if (!validateFields()) {
                return;
            }
            if (null != option) {
                switch (option) {
                    case EDIT:
                        addEditUserViewMediator.onChangeUserData();
                        window.getEditUserFrame().dispose();
                        break;
                    case ADD:
                        addEditUserViewMediator.onAddUser();
                        addEditUserViewMediator.assignUserToRoom();
                        window.getAddUserFrame().dispose();
                        break;
                    case ADMIN:
                        break;
                    default:
                        break;
                }
            }
        });
        mainPanel = new JPanel(new GridLayout(1, 2));
        dataPanel = new JPanel();
        labelPanel = new JPanel();
        editButton = new JButton();
        editButton.addActionListener((ActionEvent e) -> {
            if (option == AddUserEnum.ADMIN) {
                addEditUserViewMediator.onEditUser();
                window.getEditUserFrame().dispose();
            }
        });
        deleteButton = new JButton();
        deleteButton.addActionListener((ActionEvent e) -> {
            if (option == AddUserEnum.ADMIN) {
                addEditUserViewMediator.onDeleteUser();
                window.getEditUserFrame().dispose();
            }
        });
        userCb.addActionListener((ActionEvent e) ->{
            if(option == AddUserEnum.ADMIN && userCb.getSelectedItem() != null){
                addEditUserViewMediator.refreshUserData();
            }
        });
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
                Lookup.removeUserCertificate();
                System.exit(0);
            }
        };
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        actionMap.put("escape", escapeAction);
    }

    private Boolean validateFields() {
        Boolean validationFlag = true;
        if (option == AddUserEnum.EDIT) {
            if (!NumberFormatUtils.isInteger(phoneTf.getText())) {
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
            if (!NumberFormatUtils.isInteger(phoneTf.getText())) {
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
        return addButton;
    }

    /**
     * @param okButton the okButton to set
     */
    public void setOkButton(JButton okButton) {
        this.addButton = okButton;
    }

    public MainView getWindow() {
        return window;
    }

    public void setWindow(MainView window) {
        this.window = window;
    }

    public JComboBox getPermissionCb() {
        return permissionCb;
    }

    public void setPermissionCb(JComboBox permissionCb) {
        this.permissionCb = permissionCb;
    }

    public JLabel getPermissionContentLabel() {
        return permissionContentLabel;
    }

    public void setPermissionContentLabel(JLabel permissionContentLabel) {
        this.permissionContentLabel = permissionContentLabel;
    }

    public AddUserEnum getOption() {
        return option;
    }

    public void setOption(AddUserEnum option) {
        this.option = option;
    }

    public JLabel getRoomLabel() {
        return roomLabel;
    }

    public void setRoomLabel(JLabel roomLabel) {
        this.roomLabel = roomLabel;
    }

    public JLabel getPermissionLabel() {
        return permissionLabel;
    }

    public void setPermissionLabel(JLabel permissionLabel) {
        this.permissionLabel = permissionLabel;
    }

    public RoomComboBox getRoomCb() {
        return roomCb;
    }

    public void setRoomCb(RoomComboBox roomCb) {
        this.roomCb = roomCb;
    }

    public JLabel getRoomContentLabel() {
        return roomContentLabel;
    }

    public void setRoomContentLabel(JLabel roomContentLabel) {
        this.roomContentLabel = roomContentLabel;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public void setAddButton(JButton addButton) {
        this.addButton = addButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public void setEditButton(JButton editButton) {
        this.editButton = editButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(JButton deleteButton) {
        this.deleteButton = deleteButton;
    }

    public JLabel getChiefLabel() {
        return chiefLabel;
    }

    public void setChiefLabel(JLabel chiefLabel) {
        this.chiefLabel = chiefLabel;
    }

    public JComboBox getUserCb() {
        return userCb;
    }

    public void setUserCb(JComboBox userCb) {
        this.userCb = userCb;
    }

    public JComboBox getChiefCb() {
        return chiefCb;
    }

    public void setChiefCb(JComboBox chiefCb) {
        this.chiefCb = chiefCb;
    }

    public JLabel getChiefContentLabel() {
        return chiefContentLabel;
    }

    public void setChiefContentLabel(JLabel chiefContentLabel) {
        this.chiefContentLabel = chiefContentLabel;
    }

}
