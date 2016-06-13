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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.mediators.AssignRoomMediator;
import pl.polsl.reservations.client.mediators.AssignToUnitMediator;
import pl.polsl.reservations.client.views.utils.ButtonStyle;
import pl.polsl.reservations.client.views.utils.PanelStyle;
import pl.polsl.reservations.client.views.utils.RoomComboBox;

/**
 *
 * @author matis
 */
public class AssignToUnitView extends JPanel {

    private static final long serialVersionUID = 5936728539420292688L;

    private final int NORMAL_WIDTH = 200;
    private final int NORMAL_HEIGHT = 30;

    private final MainView window;
    private final AssignToUnitMediator assignToUnitMediator;

    private JLabel instituteLb;
    private JLabel instituteContentLb;

    private JLabel departamentLb;
    private JComboBox<String> departamentCb;

    private JLabel roomLb;
    private RoomComboBox roomCb;

    private JLabel roomTypeLb;
    private JLabel roomTypeContentLb;

    private JLabel depRoomsLb;
    private JList depRoomsLst;

    private JPanel labelPanel;
    private JPanel dataPanel;
    private JPanel mainPanel;

    private JButton addButton;

    private Image addImg;

    public AssignToUnitView(MainView window, AssignToUnitMediator assignToUnitMediator) {
        super();
        this.window = window;
        this.assignToUnitMediator = assignToUnitMediator;
        initComponents();
        prepareObjects();
        initPanels();
        setupView();
        setupListeners();
    }

    private void initComponents() {
        instituteLb = new JLabel("Institute:");
        instituteContentLb = new JLabel();
        departamentLb = new JLabel("Departaments:");
        departamentCb = new JComboBox();
        roomLb = new JLabel("Room");
        roomCb = new RoomComboBox();
        roomTypeLb = new JLabel("Room type:");
        roomTypeContentLb = new JLabel();
        depRoomsLb = new JLabel("Selected departament rooms");
        depRoomsLst = new JList();
        mainPanel = new JPanel(new GridLayout(1, 2));
        dataPanel = new JPanel();
        labelPanel = new JPanel();
        addButton = new JButton();
        keyInputDispatcher();
    }

    private void prepareObjects() {
        PanelStyle.setSize(instituteLb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(instituteContentLb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(departamentLb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(departamentCb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(roomLb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(roomTypeLb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(roomTypeContentLb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(depRoomsLb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(depRoomsLst, NORMAL_WIDTH, NORMAL_WIDTH);
        PanelStyle.setSize(labelPanel, NORMAL_WIDTH, 270);
        PanelStyle.setSize(dataPanel, NORMAL_WIDTH, 270);
        PanelStyle.setSize(mainPanel, 2 * NORMAL_WIDTH, 330);
    }

    private void setupView() {
        try {
            addImg = ImageIO.read(getClass().getResource("/resources/add.png"));
            ButtonStyle.setStyle(addButton, addImg);
        } catch (IOException ex) {
            Logger.getLogger(AddEditRoomTypeView.class.getName()).log(Level.SEVERE, null, ex);
        }
        labelPanel.add(instituteLb);
        dataPanel.add(instituteContentLb);
        labelPanel.add(departamentLb);
        dataPanel.add(departamentCb);
        labelPanel.add(roomLb);
        dataPanel.add(roomTypeLb);
        labelPanel.add(roomTypeContentLb);
        dataPanel.add(depRoomsLb);
        mainPanel.add(labelPanel);
        mainPanel.add(dataPanel);
        mainPanel.add(depRoomsLb);
        mainPanel.add(depRoomsLst);
        add(addButton);
        add(mainPanel);
    }

    private void initPanels() {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
    }
    
    private void setupListeners(){
        departamentCb.addActionListener((ActionEvent e)->{
            if(departamentCb.getSelectedItem() != null){
                assignToUnitMediator.onDepartamentChange();
            }
        });
        
        roomCb.addActionListener((ActionEvent e)->{
            if(roomCb.getSelectedItem() != null){
                assignToUnitMediator.onRoomChange();
            }
        });
        
        addButton.addActionListener((ActionEvent e)->{
            if(departamentCb.getSelectedItem() != null && roomCb.getSelectedItem() != null){
                assignToUnitMediator.onAssign();
            }
            window.getAssignRoomToDepartamentFrame().dispose();
        });
    }

    private void keyInputDispatcher() {

        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        AbstractAction escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AssignToUnitView.this.getWindow().dispose();
                Lookup.removeUserCertificate();
                System.exit(0);
            }
        };
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        actionMap.put("escape", escapeAction);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getNORMAL_WIDTH() {
        return NORMAL_WIDTH;
    }

    public int getNORMAL_HEIGHT() {
        return NORMAL_HEIGHT;
    }

    public MainView getWindow() {
        return window;
    }

    public AssignToUnitMediator getAssignRoomMediator() {
        return assignToUnitMediator;
    }

    public JLabel getInstituteLb() {
        return instituteLb;
    }

    public JLabel getInstituteContentLb() {
        return instituteContentLb;
    }

    public JLabel getDepartamentLb() {
        return departamentLb;
    }

    public JComboBox<String> getDepartamentCb() {
        return departamentCb;
    }

    public JLabel getRoomLb() {
        return roomLb;
    }

    public RoomComboBox getRoomCb() {
        return roomCb;
    }

    public JLabel getRoomTypeLb() {
        return roomTypeLb;
    }

    public JLabel getRoomTypeContentLb() {
        return roomTypeContentLb;
    }

    public JLabel getDepRoomsLb() {
        return depRoomsLb;
    }

    public JList getDepRoomsLst() {
        return depRoomsLst;
    }

    public JPanel getLabelPanel() {
        return labelPanel;
    }

    public JPanel getDataPanel() {
        return dataPanel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public Image getAddImg() {
        return addImg;
    }

}
