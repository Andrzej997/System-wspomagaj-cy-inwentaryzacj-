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
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import jdk.nashorn.internal.scripts.JO;
import pl.polsl.reservations.client.mediators.ChangePasswordViewMediator;
import pl.polsl.reservations.client.views.utils.ButtonStyle;
import pl.polsl.reservations.client.views.utils.PanelStyle;

/**
 *
 * @author abienioszek
 */
public class ChangePasswordView extends JPanel {

    private static final long serialVersionUID = 4265560895158715456L;

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

    private final ChangePasswordViewMediator changePasswordViewMediator;
    
    public ChangePasswordView(MainView window, ChangePasswordViewMediator changePasswordViewMediator) {
        this.window = window;
        this.changePasswordViewMediator = changePasswordViewMediator;
        initialize();
        setupSize();
        setupButton();
        setupPanels();
    }

    private void setupPanels() {
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
        okButton.addActionListener((ActionEvent e) -> {
            if(!new1Tf.getText().equals(new2Tf.getText())){
                JOptionPane.showMessageDialog(this, "Error !!! \n Second password is not the same as first");
            } else if (!changePasswordViewMediator.onChangePassword()) {
                JOptionPane.showMessageDialog(this, "Error !!! \n Old password is invalid");
            }
        });
        keyInputDispatcher();
    }

    private void keyInputDispatcher() {

        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        AbstractAction escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangePasswordView.this.getWindow().dispose();
                System.exit(0);
            }
        };
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        actionMap.put("escape", escapeAction);
    }

    public JLabel getOldLabel() {
        return oldLabel;
    }

    public void setOldLabel(JLabel oldLabel) {
        this.oldLabel = oldLabel;
    }

    public JLabel getNew1Label() {
        return new1Label;
    }

    public void setNew1Label(JLabel new1Label) {
        this.new1Label = new1Label;
    }

    public JLabel getNew2Label() {
        return new2Label;
    }

    public void setNew2Label(JLabel new2Label) {
        this.new2Label = new2Label;
    }

    public JTextField getOldTf() {
        return oldTf;
    }

    public void setOldTf(JTextField oldTf) {
        this.oldTf = oldTf;
    }

    public JTextField getNew1Tf() {
        return new1Tf;
    }

    public void setNew1Tf(JTextField new1Tf) {
        this.new1Tf = new1Tf;
    }

    public JTextField getNew2Tf() {
        return new2Tf;
    }

    public void setNew2Tf(JTextField new2Tf) {
        this.new2Tf = new2Tf;
    }

    public JButton getOkButton() {
        return okButton;
    }

    public void setOkButton(JButton okButton) {
        this.okButton = okButton;
    }

    public JPanel getLabelPanel() {
        return labelPanel;
    }

    public void setLabelPanel(JPanel labelPanel) {
        this.labelPanel = labelPanel;
    }

    public JPanel getDataPanel() {
        return dataPanel;
    }

    public void setDataPanel(JPanel dataPanel) {
        this.dataPanel = dataPanel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public MainView getWindow() {
        return window;
    }

    public void setWindow(MainView window) {
        this.window = window;
    }

}
