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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.border.EmptyBorder;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.mediators.AssignRoomMediator;
import pl.polsl.reservations.client.views.utils.ButtonStyle;
import pl.polsl.reservations.client.views.utils.PanelStyle;
import pl.polsl.reservations.client.views.utils.RoomComboBox;

/**
 *
 * @author matis
 */
public class AssignRoomView extends JPanel {

    private static final long serialVersionUID = 5936728539420292688L;

    private final int NORMAL_WIDTH = 200;
    private final int NORMAL_HEIGHT = 30;

    private final MainView window;
    private final AssignRoomMediator assignRoomMediator;

    private JLabel workerLb;
    private JComboBox<String> workerCb;

    private JLabel roomLb;
    private RoomComboBox roomCb;

    private JLabel currentKeeperLb;
    private JLabel currentKeeperContentLb;

    private JLabel roomTypeLb;
    private JLabel roomTypeContentLb;

    private JLabel roomSeatsLb;
    private JLabel roomSeatsContentLb;

    private JLabel asKeeperLb;
    private JCheckBox asKeeperChb;

    private JLabel currentWorkersLb;
    private JList currentWorkersLs;

    private JPanel labelPanel;
    private JPanel dataPanel;
    private JPanel mainPanel;

    private JButton addButton;

    private Image addImg;

    public AssignRoomView(MainView window, AssignRoomMediator assignRoomMediator) {
        super();
        this.window = window;
        this.assignRoomMediator = assignRoomMediator;
        initComponents();
        prepareObjects();
        setupView();
        initPanels();
        setupListeners();
    }

    private void initComponents() {
        workerLb = new JLabel("Workers:");
        workerCb = new JComboBox<>();
        roomLb = new JLabel("Room:");
        roomCb = new RoomComboBox();
        currentKeeperLb = new JLabel("Current keeper:");
        currentKeeperContentLb = new JLabel();
        roomTypeLb = new JLabel("Room type:");
        roomTypeContentLb = new JLabel();
        roomSeatsLb = new JLabel("Seats:");
        roomSeatsContentLb = new JLabel();
        asKeeperLb = new JLabel("Assign as keeper:");
        asKeeperChb = new JCheckBox();
        currentWorkersLb = new JLabel("Assigned workers");
        currentWorkersLs = new JList();
        mainPanel = new JPanel(new GridLayout(1, 2));
        dataPanel = new JPanel();
        labelPanel = new JPanel();
        addButton = new JButton();
        keyInputDispatcher();
    }

    private void prepareObjects() {
        PanelStyle.setSize(workerLb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(workerCb, NORMAL_WIDTH, NORMAL_HEIGHT, true);
        PanelStyle.setSize(roomLb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(roomCb,NORMAL_WIDTH, NORMAL_HEIGHT, true);
        PanelStyle.setSize(currentKeeperLb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(currentKeeperContentLb, NORMAL_WIDTH, NORMAL_HEIGHT, true);
        PanelStyle.setSize(roomTypeLb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(roomTypeContentLb,NORMAL_WIDTH, NORMAL_HEIGHT, true);
        PanelStyle.setSize(roomSeatsLb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(roomSeatsContentLb,NORMAL_WIDTH, NORMAL_HEIGHT, true);
        PanelStyle.setSize(asKeeperLb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(asKeeperChb,NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(currentWorkersLb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(currentWorkersLs, NORMAL_WIDTH, NORMAL_WIDTH, true);
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
        labelPanel.add(workerLb);
        dataPanel.add(workerCb);
        labelPanel.add(roomLb);
        dataPanel.add(roomCb);
        labelPanel.add(currentKeeperLb);
        dataPanel.add(currentKeeperContentLb);
        labelPanel.add(roomTypeLb);
        dataPanel.add(roomTypeContentLb);
        labelPanel.add(roomSeatsLb);
        dataPanel.add(roomSeatsContentLb);
        labelPanel.add(asKeeperLb);
        dataPanel.add(asKeeperChb);
        labelPanel.add(currentWorkersLb);
        dataPanel.add(currentWorkersLs);
        mainPanel.add(labelPanel);
        mainPanel.add(dataPanel);
        add(mainPanel);
        add(addButton);
    }

    private void setupListeners() {
        workerCb.addActionListener((ActionEvent e) -> {
            if (workerCb.getSelectedItem() != null) {
                assignRoomMediator.onUserSelectionChange();
            }
        });

        roomCb.addActionListener((ActionEvent e) -> {
            if (roomCb.getSelectedItem() != null) {
                assignRoomMediator.onRoomSelectionChange();
            }
        });

        addButton.addActionListener((ActionEvent e) -> {
            if (workerCb.getSelectedItem() != null && roomCb.getSelectedItem() != null) {
                assignRoomMediator.onAssign();
            }
            window.getAssignRoomFrame().dispose();
        });
    }

    private void initPanels() {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        
    }

    private void keyInputDispatcher() {

        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        AbstractAction escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AssignRoomView.this.getWindow().dispose();
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

    public AssignRoomMediator getAssignRoomMediator() {
        return assignRoomMediator;
    }

    public JLabel getWorkerLb() {
        return workerLb;
    }

    public JComboBox<String> getWorkerCb() {
        return workerCb;
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

    public JLabel getRoomSeatsLb() {
        return roomSeatsLb;
    }

    public JLabel getRoomSeatsContentLb() {
        return roomSeatsContentLb;
    }

    public JLabel getCurrentWorkersLb() {
        return currentWorkersLb;
    }

    public JList getCurrentWorkersLs() {
        return currentWorkersLs;
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

    public JLabel getAsKeeperLb() {
        return asKeeperLb;
    }

    public JCheckBox getAsKeeperChb() {
        return asKeeperChb;
    }

    public JLabel getCurrentKeeperContentLb() {
        return currentKeeperContentLb;
    }

}
