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
import pl.polsl.reservations.client.views.utils.ButtonStyle;
import pl.polsl.reservations.client.views.utils.PanelStyle;

/**
 *
 * @author abienioszek
 */
public class CreateRaportView extends JPanel {

    private static final long serialVersionUID = -2093933144982918107L;

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
        if (option < 3) {
            PanelStyle.setSize(dataPanel, NORMAL_WIDTH, 120);
            PanelStyle.setSize(mainPanel, 2 * NORMAL_WIDTH, 120);
            PanelStyle.setSize(this, 2 * NORMAL_WIDTH, 190);
        } else {
            PanelStyle.setSize(dataPanel, NORMAL_WIDTH, 50);
            PanelStyle.setSize(mainPanel, 2 * NORMAL_WIDTH, 50);
            PanelStyle.setSize(this, 2 * NORMAL_WIDTH, 120);
        }

    }

    private void initializeObjects() {
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
                CreateRaportView.this.getWindow().dispose();
                System.exit(0);
            }
        };
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        actionMap.put("escape", escapeAction);
    }

    public MainView getWindow() {
        return window;
    }

    public void setWindow(MainView window) {
        this.window = window;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public JTextField getRoomIdTf() {
        return roomIdTf;
    }

    public void setRoomIdTf(JTextField roomIdTf) {
        this.roomIdTf = roomIdTf;
    }

    public JTextField getNumberTf() {
        return numberTf;
    }

    public void setNumberTf(JTextField numberTf) {
        this.numberTf = numberTf;
    }

    public JComboBox getTypeCb() {
        return typeCb;
    }

    public void setTypeCb(JComboBox typeCb) {
        this.typeCb = typeCb;
    }

    public JComboBox getKeeperCb() {
        return keeperCb;
    }

    public void setKeeperCb(JComboBox keeperCb) {
        this.keeperCb = keeperCb;
    }

    public JComboBox getDepartmentCb() {
        return departmentCb;
    }

    public void setDepartmentCb(JComboBox departmentCb) {
        this.departmentCb = departmentCb;
    }

    public JComboBox getStateCb() {
        return stateCb;
    }

    public void setStateCb(JComboBox stateCb) {
        this.stateCb = stateCb;
    }

    public JTextField getNameTf() {
        return nameTf;
    }

    public void setNameTf(JTextField nameTf) {
        this.nameTf = nameTf;
    }

    public JLabel getRoomLabel() {
        return roomLabel;
    }

    public void setRoomLabel(JLabel roomLabel) {
        this.roomLabel = roomLabel;
    }

    public JLabel getNumberLabel() {
        return numberLabel;
    }

    public void setNumberLabel(JLabel numberLabel) {
        this.numberLabel = numberLabel;
    }

    public JLabel getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(JLabel typeLabel) {
        this.typeLabel = typeLabel;
    }

    public JLabel getKeeperLabel() {
        return keeperLabel;
    }

    public void setKeeperLabel(JLabel keeperLabel) {
        this.keeperLabel = keeperLabel;
    }

    public JLabel getDepartmentLabel() {
        return departmentLabel;
    }

    public void setDepartmentLabel(JLabel departmentLabel) {
        this.departmentLabel = departmentLabel;
    }

    public JLabel getStateLabel() {
        return stateLabel;
    }

    public void setStateLabel(JLabel stateLabel) {
        this.stateLabel = stateLabel;
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(JLabel nameLabel) {
        this.nameLabel = nameLabel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public JPanel getDataPanel() {
        return dataPanel;
    }

    public void setDataPanel(JPanel dataPanel) {
        this.dataPanel = dataPanel;
    }

    public JPanel getLabelPanel() {
        return labelPanel;
    }

    public void setLabelPanel(JPanel labelPanel) {
        this.labelPanel = labelPanel;
    }

    public JButton getOkButton() {
        return okButton;
    }

    public void setOkButton(JButton okButton) {
        this.okButton = okButton;
    }

}
