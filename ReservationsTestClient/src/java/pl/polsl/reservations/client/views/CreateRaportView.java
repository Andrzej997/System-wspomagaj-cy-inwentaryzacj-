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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import pl.polsl.reservations.client.views.utils.ButtonStyle;
import pl.polsl.reservations.client.views.utils.PanelStyle;

/**
 *
 * @author abienioszek
 */
public class CreateRaportView extends JPanel {

    private final int NORMAL_WIDTH = 200;
    private final int NORMAL_HEIGHT = 30;

    private MainView window;
    private int option;

    private JTextField roomIdTf;
    private JTextField numberTf;
    private JComboBox typeCb;
    private JComboBox keeperCb;
    private JComboBox departmentCb;
    private JComboBox stateCb;
    private JTextField nameTf;

    private JLabel roomLabel;
    private JLabel numberLabel;
    private JLabel typeLabel;
    private JLabel keeperLabel;
    private JLabel departmentLabel;
    private JLabel stateLabel;
    private JLabel nameLabel;

    private JPanel mainPanel;
    private JPanel dataPanel;
    private JPanel labelPanel;

    private JButton okButton;

    public CreateRaportView(MainView window, int option) {
        this.window = window;
        this.option = option;
        initializeObjects();
        initPanels();
        setupSize();
        setupButton();
        setupView();
    }

    private void setupView() {
        if (option > 2) {
            labelPanel.add(nameLabel);
            dataPanel.add(nameTf);
        } else if (option == 1) {
            labelPanel.add(roomLabel);
            labelPanel.add(numberLabel);
            labelPanel.add(departmentLabel);
            labelPanel.add(keeperLabel);
            labelPanel.add(typeLabel);
            dataPanel.add(roomIdTf);
            dataPanel.add(numberTf);
            dataPanel.add(departmentCb);
            dataPanel.add(keeperCb);
            dataPanel.add(typeCb);

        } else if (option == 2) {
            labelPanel.add(typeLabel);
            labelPanel.add(numberLabel);
            labelPanel.add(nameLabel);
            labelPanel.add(stateLabel);
            dataPanel.add(typeCb);
            dataPanel.add(numberTf);
            dataPanel.add(nameTf);
            dataPanel.add(stateCb);
        }
        mainPanel.add(labelPanel);
        mainPanel.add(dataPanel);
        add(mainPanel);
        add(okButton);
    }

    private void setupButton() {
        try {
            Image img = ImageIO.read(getClass().getResource("/resources/ok.png"));
            ButtonStyle.setStyle(okButton, img);
        } catch (IOException ex) {
            System.out.println("RESOURCE ERROR: " + ex.toString());
        }
    }

    private void setupSize() {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        PanelStyle.setSize(roomLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(numberLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(typeLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(stateLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(departmentLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(keeperLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(nameLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(roomIdTf, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(numberTf, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(typeCb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(stateCb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(departmentCb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(keeperCb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(nameTf, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(labelPanel, NORMAL_WIDTH, 120);
        if(option<3){
        PanelStyle.setSize(dataPanel, NORMAL_WIDTH, 120);
        PanelStyle.setSize(mainPanel, 2 * NORMAL_WIDTH, 120);
        PanelStyle.setSize(this,2 * NORMAL_WIDTH, 190);
        }
        else {
             PanelStyle.setSize(dataPanel, NORMAL_WIDTH, 50);
        PanelStyle.setSize(mainPanel, 2 * NORMAL_WIDTH, 50);
        PanelStyle.setSize(this,2 * NORMAL_WIDTH, 120); 
        }

    }

    public void initializeObjects() {
        okButton = new JButton();
        roomIdTf = new JTextField();
        numberTf = new JTextField();
        typeCb = new JComboBox();
        keeperCb = new JComboBox();
        departmentCb = new JComboBox();
        stateCb = new JComboBox();
        nameTf = new JTextField();
        roomLabel = new JLabel("Room ID: ");
        numberLabel = new JLabel("Number of seats: ");
        typeLabel = new JLabel("Type: ");
        keeperLabel = new JLabel("Keeper: ");
        departmentLabel = new JLabel("Department: ");
        stateLabel = new JLabel("State:");
        switch (option) {
            case 3:
                nameLabel = new JLabel("New state name:");
                break;
            case 4:
                nameLabel = new JLabel("New type name: ");
                break;
            default:
                nameLabel = new JLabel("Name: ");
                break;
        }
        mainPanel = new JPanel(new GridLayout(1, 2));
        dataPanel = new JPanel();
        labelPanel = new JPanel();
    }

    private void initPanels() {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
    }

}
