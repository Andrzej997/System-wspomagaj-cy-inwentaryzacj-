/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.reservations.client.views;

import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import pl.polsl.reservations.client.views.utils.ButtonStyle;
import pl.polsl.reservations.client.views.utils.PanelStyle;

/**
 *
 * @author abienioszek
 */
public class AddEditUserView extends JPanel {

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

    public AddEditUserView(MainView window, boolean editUser) {
        super();
        this.window = window;
        this.editUser = editUser;
        initComponents();
        initPanels();
        PanelStyle.setSize(this, 800, 600);
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
        PanelStyle.setSize(labelPanel, NORMAL_WIDTH, 400);
        PanelStyle.setSize(dataPanel, NORMAL_WIDTH, 400);
          PanelStyle.setSize(mainPanel, 2*NORMAL_WIDTH, 400);
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

        mainPanel = new JPanel(new GridLayout(1, 2));
        dataPanel = new JPanel();
        labelPanel = new JPanel();
    }

    private void initPanels() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
    }

}
