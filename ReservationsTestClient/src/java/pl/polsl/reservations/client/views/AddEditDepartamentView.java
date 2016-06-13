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
import pl.polsl.reservations.client.mediators.AddEditDepartamentMediator;
import pl.polsl.reservations.client.views.utils.ButtonStyle;
import pl.polsl.reservations.client.views.utils.PanelStyle;
import pl.polsl.reservations.client.views.utils.ValidationErrorMessanger;

/**
 *
 * @author matis
 */
public class AddEditDepartamentView extends JPanel {

    private static final long serialVersionUID = -6975785500186251759L;

    private final int NORMAL_WIDTH = 200;
    private final int NORMAL_HEIGHT = 30;

    private JLabel departamentLb;
    private JComboBox<String> departamentCb;

    private JLabel nameLb;
    private JTextField nameTf;

    private JLabel instituteLb;
    private JComboBox<String> instituteCb;

    private JLabel chiefLb;
    private JComboBox<String> chiefCb;

    private JButton addButton;
    private JButton deleteButton;

    private JPanel labelPanel;
    private JPanel dataPanel;
    private JPanel mainPanel;
    private JPanel navPanel;

    private Image addImg;
    private Image editImg;
    private Image deleteImg;

    private final MainView window;
    private final AddEditDepartamentMediator addEditDepartamentMediator;

    public AddEditDepartamentView(MainView window, AddEditDepartamentMediator addEditDepartamentMediator) {
        super();
        this.window = window;
        this.addEditDepartamentMediator = addEditDepartamentMediator;
        initComponents();
        prepareObjects();
        initPanels();
        setupView();
        setupListeners();
    }

    private void initComponents() {
        departamentLb = new JLabel("Departament:");
        departamentCb = new JComboBox<>();
        nameLb = new JLabel("Name:");
        nameTf = new JTextField();
        instituteLb = new JLabel("Institute");
        instituteCb = new JComboBox<>();
        chiefLb = new JLabel("Chief:");
        chiefCb = new JComboBox<>();
        addButton = new JButton();
        deleteButton = new JButton();
        labelPanel = new JPanel();
        dataPanel = new JPanel();
        mainPanel = new JPanel();
        navPanel = new JPanel();
        keyInputDispatcher();
    }

    private void prepareObjects() {
        PanelStyle.setSize(departamentLb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(departamentCb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(nameLb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(nameTf, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(instituteLb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(instituteCb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(chiefLb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(chiefCb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(labelPanel, NORMAL_WIDTH, 4 * NORMAL_HEIGHT);
        PanelStyle.setSize(dataPanel, NORMAL_WIDTH, 4 * NORMAL_HEIGHT);
    }

    private void initPanels() {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.X_AXIS));
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
        labelPanel.add(departamentLb);
        dataPanel.add(departamentCb);
        labelPanel.add(nameLb);
        dataPanel.add(nameTf);
        labelPanel.add(instituteLb);
        dataPanel.add(instituteCb);
        labelPanel.add(chiefLb);
        dataPanel.add(chiefCb);
        mainPanel.add(labelPanel);
        mainPanel.add(dataPanel);
        navPanel.add(addButton);
        navPanel.add(deleteButton);
        add(mainPanel);
        add(navPanel);
    }

    private void setupListeners() {
        departamentCb.addActionListener((ActionEvent e) -> {
            if (departamentCb.getSelectedItem() != null) {
                addEditDepartamentMediator.onSelectionChange();
            }
        });

        addButton.addActionListener((ActionEvent e) -> {
            if (!validateAll()) {
                return;
            }
            addEditDepartamentMediator.onAddEdit();
            window.getAddEditDepartamentFrame().dispose();
        });

        deleteButton.addActionListener((ActionEvent e) -> {
            if (!validateAll()) {
                return;
            }
            addEditDepartamentMediator.onDelete();
            window.getAddEditDepartamentFrame().dispose();
        });
    }

    private Boolean validateAll() {
        Boolean validationFlag = true;
        if (nameTf.getText().isEmpty()) {
            ValidationErrorMessanger.showErrorMessage(nameTf, "Name field cannot be empty");
            validationFlag = false;
        }
        return validationFlag;
    }

    private void keyInputDispatcher() {

        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        AbstractAction escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEditDepartamentView.this.getWindow().dispose();
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

    public JLabel getDepartamentLb() {
        return departamentLb;
    }

    public JComboBox<String> getDepartamentCb() {
        return departamentCb;
    }

    public JLabel getNameLb() {
        return nameLb;
    }

    public JTextField getNameTf() {
        return nameTf;
    }

    public JLabel getInstituteLb() {
        return instituteLb;
    }

    public JComboBox<String> getInstituteCb() {
        return instituteCb;
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

    public AddEditDepartamentMediator getAddEditDepartamentMediator() {
        return addEditDepartamentMediator;
    }

    public JLabel getChiefLb() {
        return chiefLb;
    }

    public JComboBox<String> getChiefCb() {
        return chiefCb;
    }

}
