package pl.polsl.reservations.client.views;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import pl.polsl.reservations.client.mediators.AddEditRoomTypeViewMediator;
import pl.polsl.reservations.client.views.utils.ButtonStyle;
import pl.polsl.reservations.client.views.utils.PanelStyle;

/**
 *
 * @author matis
 */
public class AddEditRoomTypeView extends JPanel {

    private static final long serialVersionUID = 5936728539420292688L;

    private final int NORMAL_WIDTH = 200;
    private final int NORMAL_HEIGHT = 30;

    private final MainView window;

    private JLabel roomTypeLabel;
    private JLabel roomTypeDescLabel;

    private JTextField roomTypeField;
    private JTextField roomTypeDescField;
    private JLabel roomTypesLb;

    private JPanel labelPanel;
    private JPanel dataPanel;
    private JPanel mainPanel;
    private JButton addButton;
    private JButton deleteButton;

    private Image addImg;
    private Image editImg;
    private Image deleteImg;

    private JComboBox<String> roomTypes;

    private final AddEditRoomTypeViewMediator addEditRoomTypeViewMediator;

    public AddEditRoomTypeView(MainView window, AddEditRoomTypeViewMediator addEditRoomTypeViewMediator) {
        super();
        this.window = window;
        this.addEditRoomTypeViewMediator = addEditRoomTypeViewMediator;
        initComponents();
        prepareObjects();
        setupView();
        initPanels();
        setupListeners();
    }

    private void initComponents() {
        roomTypeLabel = new JLabel("Room type: ");
        roomTypeDescLabel = new JLabel("Type description :");
        roomTypeField = new JTextField();
        roomTypeDescField = new JTextField();
        roomTypesLb = new JLabel("Select type: ");
        roomTypes = new JComboBox<>();
        mainPanel = new JPanel(new GridLayout(1, 2));
        dataPanel = new JPanel();
        labelPanel = new JPanel();
        addButton = new JButton();
        deleteButton = new JButton();
        keyInputDispatcher();
    }

    private void prepareObjects() {
        PanelStyle.setSize(roomTypeLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(roomTypeDescLabel, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(roomTypeField, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(roomTypeDescField, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(roomTypesLb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(roomTypes, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(labelPanel, NORMAL_WIDTH, 270);
        PanelStyle.setSize(dataPanel, NORMAL_WIDTH, 270);
        PanelStyle.setSize(mainPanel, 2 * NORMAL_WIDTH, 330);
    }

    private void setupView() {

        Image img;
        try {
            addImg = ImageIO.read(getClass().getResource("/resources/add.png"));
            ButtonStyle.setStyle(addButton, addImg);
            editImg = ImageIO.read(getClass().getResource("/resources/ok.png"));
            deleteImg = ImageIO.read(getClass().getResource("/resources/error.png"));
            ButtonStyle.setStyle(deleteButton, deleteImg);
        } catch (IOException ex) {
            Logger.getLogger(AddEditRoomTypeView.class.getName()).log(Level.SEVERE, null, ex);
        }
        labelPanel.add(roomTypesLb);
        labelPanel.add(roomTypeLabel);
        labelPanel.add(roomTypeDescLabel);
        dataPanel.add(roomTypes);
        dataPanel.add(roomTypeField);
        dataPanel.add(roomTypeDescField);
        mainPanel.add(labelPanel);
        mainPanel.add(dataPanel);
        add(addButton);
        add(deleteButton);
    }

    private void initPanels() {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
    }

    private void setupListeners() {
        roomTypes.addActionListener((ActionEvent e) -> {
            addEditRoomTypeViewMediator.onSelectionChange();
        });
        
        addButton.addActionListener((ActionEvent e)->{
            addEditRoomTypeViewMediator.onAddEdit();
            window.getAddRoomTypeFrame().dispose();
        });
        
        deleteButton.addActionListener((ActionEvent e)->{
            addEditRoomTypeViewMediator.onDelete();
            window.getAddRoomTypeFrame().dispose();
        });
    }

    private void keyInputDispatcher() {

        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        AbstractAction escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEditRoomTypeView.this.getWindow().dispose();
                Lookup.removeUserCertificate();
                System.exit(0);
            }
        };
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        actionMap.put("escape", escapeAction);
    }

    public MainView getWindow() {
        return window;
    }

    public JLabel getRoomTypeLabel() {
        return roomTypeLabel;
    }

    public void setRoomTypeLabel(JLabel roomTypeLabel) {
        this.roomTypeLabel = roomTypeLabel;
    }

    public JLabel getRoomTypeDescLabel() {
        return roomTypeDescLabel;
    }

    public void setRoomTypeDescLabel(JLabel roomTypeDescLabel) {
        this.roomTypeDescLabel = roomTypeDescLabel;
    }

    public JTextField getRoomTypeField() {
        return roomTypeField;
    }

    public void setRoomTypeField(JTextField roomTypeField) {
        this.roomTypeField = roomTypeField;
    }

    public JTextField getRoomTypeDescField() {
        return roomTypeDescField;
    }

    public void setRoomTypeDescField(JTextField roomTypeDescField) {
        this.roomTypeDescField = roomTypeDescField;
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

    public JComboBox<String> getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(JComboBox<String> roomTypes) {
        this.roomTypes = roomTypes;
    }

    public JLabel getRoomTypesLb() {
        return roomTypesLb;
    }

    public void setRoomTypesLb(JLabel roomTypesLb) {
        this.roomTypesLb = roomTypesLb;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public void setAddButton(JButton addButton) {
        this.addButton = addButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(JButton deleteButton) {
        this.deleteButton = deleteButton;
    }

    public Image getAddImg() {
        return addImg;
    }

    public void setAddImg(Image addImg) {
        this.addImg = addImg;
    }

    public Image getEditImg() {
        return editImg;
    }

    public void setEditImg(Image editImg) {
        this.editImg = editImg;
    }

    public Image getDeleteImg() {
        return deleteImg;
    }

    public void setDeleteImg(Image deleteImg) {
        this.deleteImg = deleteImg;
    }
}
