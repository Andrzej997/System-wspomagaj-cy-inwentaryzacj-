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
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import pl.polsl.reservations.client.mediators.TutorialViewMediator;
import pl.polsl.reservations.client.views.utils.ButtonStyle;
import pl.polsl.reservations.client.views.utils.PanelStyle;

/**
 *
 * @author Ola
 */
public class TutorialView extends JPanel {

    private static final long serialVersionUID = 5902382055394831826L;

    private MainView window;
    private int counter = 0;
    private JButton prevBtn;
    private JButton nextBtn;
    private JLabel picLabel;
    private JLabel textLabel;
    private JPanel mainPanel;

    private final TutorialViewMediator tutorialViewMediator;

    public TutorialView(MainView window, TutorialViewMediator tutorialViewMediator) {
        this.window = window;
        this.tutorialViewMediator = tutorialViewMediator;
        initComponents();
    }

    private void initComponents() {
        initObjects();
        try {
            Image img = ImageIO.read(getClass().getResource("/resources/left.png"));
            ButtonStyle.setStyle(prevBtn, img);
            Image img2 = ImageIO.read(getClass().getResource("/resources/right.png"));
            ButtonStyle.setStyle(nextBtn, img2);
            setupTutorial();
        } catch (IOException e) {
            System.out.println("RESOURCE ERROR: " + e.toString());
        }
        setListeners();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.add(prevBtn);
        mainPanel.add(picLabel);
        mainPanel.add(nextBtn);
        add(mainPanel);
        add(textLabel);
        keyInputDispatcher();
    }

    private void setListeners() {
        prevBtn.addActionListener((ActionEvent e) -> {
            try {
                counter--;
                setupTutorial();
            } catch (IOException ex) {
                Logger.getLogger(TutorialView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        nextBtn.addActionListener((ActionEvent e) -> {
            try {
                counter++;
                setupTutorial();
            } catch (IOException ex) {
                Logger.getLogger(TutorialView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void setupTutorial() throws IOException {
        Image myPicture;
        switch (counter) {
            case 0:
                myPicture = ImageIO.read(getClass().getResource("/resources/login.png"));
                textLabel.setText("TEST1");
                break;
            case 1:
                myPicture = ImageIO.read(getClass().getResource("/resources/add.png"));
                 textLabel.setText("TEST2");
                break;
            case 2:
                myPicture = ImageIO.read(getClass().getResource("/resources/back.png"));
                break;
            case 3:
                myPicture = ImageIO.read(getClass().getResource("/resources/left.png"));
                break;
            case 4:
                myPicture = ImageIO.read(getClass().getResource("/resources/login.png"));
                break;
            case 5:
                myPicture = ImageIO.read(getClass().getResource("/resources/login.png"));
                break;
            case 6:
                myPicture = ImageIO.read(getClass().getResource("/resources/login.png"));
                break;
            default:
                myPicture = ImageIO.read(getClass().getResource("/resources/login.png"));
                break;
        }
        picLabel.setIcon(new ImageIcon(myPicture.getScaledInstance(250, 250,
                java.awt.Image.SCALE_SMOOTH)));
        picLabel.repaint();
        textLabel.repaint();
    }

    private void initObjects() {
        picLabel = new JLabel("");
        picLabel.setBorder(new EmptyBorder(20, 20, 20, 20));
        prevBtn = new JButton();
        nextBtn = new JButton();
        textLabel = new JLabel();
        PanelStyle.setSize(textLabel, 300, 50);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        PanelStyle.setSize(this, 500, 370);
    }

    private void keyInputDispatcher() {

        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        AbstractAction escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TutorialView.this.getWindow().dispose();
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

}
