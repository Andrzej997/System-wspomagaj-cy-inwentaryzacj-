package pl.polsl.reservations.client.views;

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
import pl.polsl.reservations.client.mediators.AddEditInstituteMediator;
import pl.polsl.reservations.client.views.utils.ButtonStyle;
import pl.polsl.reservations.client.views.utils.PanelStyle;

/**
 *
 * @author matis
 */
public class AddEditInstituteView extends JPanel {

    private static final long serialVersionUID = 1364615314569519183L;

    private final int NORMAL_WIDTH = 200;
    private final int NORMAL_HEIGHT = 30;

    private JLabel instituteLb;
    private JComboBox<String> instituteCb;

    private JLabel nameLb;
    private JTextField nameTf;

    private JButton addButton;
    private JButton deleteButton;

    private JPanel labelPanel;
    private JPanel dataPanel;
    private JPanel mainPanel;

    private Image addImg;
    private Image editImg;
    private Image deleteImg;

    private final MainView window;
    private final AddEditInstituteMediator addEditInstituteMediator;

    public AddEditInstituteView(MainView window, AddEditInstituteMediator addEditInstituteMediator) {
        super();
        this.window = window;
        this.addEditInstituteMediator = addEditInstituteMediator;
        initComponents();
        prepareObjects();
        initPanels();
        setupView();
        setupListeners();
    }

    private void initComponents() {
        instituteLb = new JLabel("Institute");
        instituteCb = new JComboBox<>();
        nameLb = new JLabel("Institute name");
        nameTf = new JTextField();
        addButton = new JButton();
        deleteButton = new JButton();
        labelPanel = new JPanel();
        dataPanel = new JPanel();
        mainPanel = new JPanel();
        keyInputDispatcher();
    }

    private void prepareObjects() {
        PanelStyle.setSize(instituteLb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(instituteCb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(nameLb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(nameTf, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(labelPanel, NORMAL_WIDTH, 270);
        PanelStyle.setSize(dataPanel, NORMAL_WIDTH, 270);
        PanelStyle.setSize(mainPanel, 2 * NORMAL_WIDTH, 330);
    }

    private void initPanels() {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
    }

    private void setupView() {
        try {
            addImg = ImageIO.read(getClass().getResource("/resources/add.png"));
            ButtonStyle.setStyle(addButton, addImg);
            editImg = ImageIO.read(getClass().getResource("/resources/ok.png"));
            deleteImg = ImageIO.read(getClass().getResource("/resources/error.png"));
            ButtonStyle.setStyle(deleteButton, deleteImg);
        } catch (IOException ex) {
            Logger.getLogger(AddEditRoomTypeView.class.getName()).log(Level.SEVERE, null, ex);
        }
        labelPanel.add(instituteLb);
        dataPanel.add(instituteCb);
        labelPanel.add(nameLb);
        dataPanel.add(nameTf);
        mainPanel.add(labelPanel);
        mainPanel.add(dataPanel);
        mainPanel.add(addButton);
        mainPanel.add(deleteButton);
        add(mainPanel);
    }

    private void setupListeners() {
        instituteCb.addActionListener((ActionEvent e) -> {
            addEditInstituteMediator.onSelectionChange();
        });
        
        addButton.addActionListener((ActionEvent e)->{
            addEditInstituteMediator.onAddEdit();
            window.getAddEditInstituteFrame().dispose();
        });
        
        deleteButton.addActionListener((ActionEvent e)->{
            addEditInstituteMediator.onDelete();
            window.getAddEditInstituteFrame().dispose();
        });
    }

    private void keyInputDispatcher() {

        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        AbstractAction escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEditInstituteView.this.getWindow().dispose();
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

    public JLabel getInstituteLb() {
        return instituteLb;
    }

    public JComboBox<String> getInstituteCb() {
        return instituteCb;
    }

    public JLabel getNameLb() {
        return nameLb;
    }

    public JTextField getNameTf() {
        return nameTf;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
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

    public Image getAddImg() {
        return addImg;
    }

    public Image getEditImg() {
        return editImg;
    }

    public Image getDeleteImg() {
        return deleteImg;
    }

    public MainView getWindow() {
        return window;
    }

    public AddEditInstituteMediator getAddEditInstituteMediator() {
        return addEditInstituteMediator;
    }

}
