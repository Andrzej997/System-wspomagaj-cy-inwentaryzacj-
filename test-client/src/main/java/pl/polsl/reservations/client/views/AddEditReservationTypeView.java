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
import pl.polsl.reservations.client.mediators.AddEditReservationTypeMediator;
import pl.polsl.reservations.client.views.utils.ButtonStyle;
import pl.polsl.reservations.client.views.utils.PanelStyle;
import pl.polsl.reservations.client.views.utils.ValidationErrorMessanger;

/**
 *
 * @author matis
 */
public class AddEditReservationTypeView extends JPanel {

    private static final long serialVersionUID = 1364615314569519183L;

    private static final int NORMAL_WIDTH = 200;
    private static final int NORMAL_HEIGHT = 30;

    private JLabel typeLb;
    private JComboBox<String> typeCb;

    private JLabel shortDescLb;
    private JTextField shortDescTf;

    private JLabel longDescLb;
    private JTextField longDescTf;

    private JLabel colorLb;
    private JComboBox<String> colorCb;

    private JButton addButton;
    private JButton deleteButton;

    private JPanel labelPanel;
    private JPanel dataPanel;
    private JPanel mainPanel;
    private JPanel navPanel;

    private transient Image addImg;
    private transient Image editImg;
    private transient Image deleteImg;

    private MainView window;
    private final transient AddEditReservationTypeMediator addEditReservationTypeMediator;

    public AddEditReservationTypeView(MainView window, AddEditReservationTypeMediator addEditReservationTypeMediator) {
        super();
        this.window = window;
        this.addEditReservationTypeMediator = addEditReservationTypeMediator;
        initComponents();
        prepareObjects();
        initPanels();
        setupView();
        setupListeners();
    }

    private void initComponents() {
        typeLb = new JLabel("Reservation types:");
        typeCb = new JComboBox<>();
        shortDescLb = new JLabel("Type:");
        shortDescTf = new JTextField();
        longDescLb = new JLabel("Description:");
        longDescTf = new JTextField();
        colorLb = new JLabel("Color:");
        colorCb = new JComboBox<>();
        addButton = new JButton();
        deleteButton = new JButton();
        labelPanel = new JPanel();
        dataPanel = new JPanel();
        mainPanel = new JPanel();
        navPanel = new JPanel();
        keyInputDispatcher();
    }

    private void prepareObjects() {
        PanelStyle.setSize(typeLb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(typeCb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(shortDescLb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(shortDescTf, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(longDescLb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(longDescTf, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(colorLb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(colorCb, NORMAL_WIDTH, NORMAL_HEIGHT);
        PanelStyle.setSize(labelPanel, NORMAL_WIDTH, 4 * NORMAL_HEIGHT);
        PanelStyle.setSize(dataPanel, NORMAL_WIDTH, 4 * NORMAL_HEIGHT);
    }

    private void setupView() {
        try {
            addImg = ImageIO.read(AddEditReservationTypeView.class.getResource("/resources/add.png"));
            ButtonStyle.setStyle(addButton, addImg);
            editImg = ImageIO.read(AddEditReservationTypeView.class.getResource("/resources/ok.png"));
            deleteImg = ImageIO.read(AddEditReservationTypeView.class.getResource("/resources/error.png"));
            ButtonStyle.setStyle(deleteButton, deleteImg);
        } catch (IOException ex) {
            Logger.getLogger(AddEditRoomTypeView.class.getName()).log(Level.SEVERE, null, ex);
        }
        labelPanel.add(typeLb);
        labelPanel.add(shortDescLb);
        labelPanel.add(longDescLb);
        labelPanel.add(colorLb);
        dataPanel.add(typeCb);
        dataPanel.add(shortDescTf);
        dataPanel.add(longDescTf);
        dataPanel.add(colorCb);
        mainPanel.add(labelPanel);
        mainPanel.add(dataPanel);
        navPanel.add(addButton);
        navPanel.add(deleteButton);
        add(mainPanel);
        add(navPanel);
    }

    private void initPanels() {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.X_AXIS));
    }

    private void setupListeners() {
        typeCb.addActionListener((ActionEvent e) -> {
            if (typeCb.getSelectedItem() != null) {
                addEditReservationTypeMediator.onSelectionChange();
            }
        });

        addButton.addActionListener((ActionEvent e) -> {
            if (!validateAll()) {
                return;
            }
            addEditReservationTypeMediator.onAddEdit();
            window.getAddEditReservationTypeFrame().dispose();
        });

        deleteButton.addActionListener((ActionEvent e) -> {
            if (!validateAll()) {
                return;
            }
            addEditReservationTypeMediator.onDelete();
            window.getAddEditReservationTypeFrame().dispose();
        });
    }

    private Boolean validateAll() {
        Boolean validationFlag = true;
        if (shortDescTf.getText().isEmpty()) {
            ValidationErrorMessanger.showErrorMessage(shortDescTf, "Type field cannot be empty");
            validationFlag = false;
        }
        if (longDescTf.getText().isEmpty()) {
            ValidationErrorMessanger.showErrorMessage(longDescTf, "Description field cannot be empty");
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
                AddEditReservationTypeView.this.getWindow().dispose();
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getNORMAL_WIDTH() {
        return NORMAL_WIDTH;
    }

    public int getNORMAL_HEIGHT() {
        return NORMAL_HEIGHT;
    }

    public JLabel getTypeLb() {
        return typeLb;
    }

    public JComboBox<String> getTypeCb() {
        return typeCb;
    }

    public JLabel getShortDescLb() {
        return shortDescLb;
    }

    public JTextField getShortDescTf() {
        return shortDescTf;
    }

    public JLabel getLongDescLb() {
        return longDescLb;
    }

    public JTextField getLongDescTf() {
        return longDescTf;
    }

    public JLabel getColorLb() {
        return colorLb;
    }

    public JComboBox<String> getColorCb() {
        return colorCb;
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

    public AddEditReservationTypeMediator getAddEditReservationTypeMediator() {
        return addEditReservationTypeMediator;
    }

}
