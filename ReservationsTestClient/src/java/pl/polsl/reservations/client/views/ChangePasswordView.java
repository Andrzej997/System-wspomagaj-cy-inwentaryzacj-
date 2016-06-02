/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.reservations.client.views;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
public class ChangePasswordView extends JPanel {

    private final int NORMAL_WIDTH = 200;
    private final int NORMAL_HEIGHT = 30;

    private MainView window;
    private JLabel oldLabel;
    private JLabel new1Label;
    private JLabel new2Label;
    private JTextField oldTf;
    private JTextField new1Tf;
    private JTextField new2Tf;
    private JButton okButton;
    private JPanel labelPanel;
    private JPanel dataPanel;
    private JPanel mainPanel;

    public ChangePasswordView(MainView window) {
        this.window = window;
        initialize();
        setupSize();
        setupButton();
        setupPanels();
    }
    
    private void setupPanels(){
        labelPanel.add(oldLabel);
        labelPanel.add(new1Label);
        labelPanel.add(new2Label);
        dataPanel.add(oldTf);
        dataPanel.add(new1Tf);
        dataPanel.add(new2Tf);
        mainPanel.add(labelPanel);
        mainPanel.add(dataPanel);
        add(mainPanel);
        add(okButton);
    }
    
    private void setupButton(){
         try {
            Image img = ImageIO.read(getClass().getResource("/resources/ok.png"));
            ButtonStyle.setStyle(okButton, img);
        } catch (IOException ex) {
            System.out.println("RESOURCE ERROR: " + ex.toString());
        }
    }

    private void setupSize() {
         setBorder(new EmptyBorder(10, 10, 10, 10));
        PanelStyle.setSize(this, 400, 150);
        PanelStyle.setSize(oldLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(oldTf, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(new1Label, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(new1Tf, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(new2Label, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(new2Tf, NORMAL_WIDTH, NORMAL_HEIGHT);

    }

    private void initialize() {
        oldLabel = new JLabel("Old password: ");
        new1Label = new JLabel("New password: ");
        new2Label = new JLabel("Confirm new password: ");
        oldTf = new JTextField();
        new1Tf = new JTextField();
        new2Tf = new JTextField();
        okButton = new JButton();
        dataPanel = new JPanel();
        labelPanel = new JPanel();
        mainPanel = new JPanel(new GridLayout(1, 2));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
    }

}
