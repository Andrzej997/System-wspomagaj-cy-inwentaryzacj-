package pl.polsl.reservations.client.views;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
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

    private final TutorialViewMediator tutorialViewMediator;

    public TutorialView(MainView window, TutorialViewMediator tutorialViewMediator) {
        this.window = window;
        this.tutorialViewMediator = tutorialViewMediator;

    }

    private void initComponents() {
        initObjects();
        try{
            Image img = ImageIO.read(getClass().getResource("/resources/left.png"));
            ButtonStyle.setStyle(prevBtn, img);
            Image img2 = ImageIO.read(getClass().getResource("/resources/right.png"));
            ButtonStyle.setStyle(nextBtn, img2);
            setupTutorial();}
        catch(IOException e){
            System.out.println("RESOURCE ERROR: " + e.toString()); 
        }
        setSize();
        setListeners();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(picLabel);
        keyInputDispatcher();
    }
    
    private void setListeners(){
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
    
    private void setSize(){
        setBorder(new EmptyBorder(10, 10, 10, 10));
        PanelStyle.setSize(this, 500, 500);
        PanelStyle.setSize(picLabel, 400, 400);
    }

    private void setupTutorial() throws IOException {
        String path = "";
        switch(counter){
            case 0:
                path = "/resources/login.png";
                break;
            case 1:
                  path = "/resources/add.png";
                break;
            case 2:
                break;
            case 3:
                break;
            case 4: 
                break; 
            case 5:
                break;
            case 6:
                break;
        }
        BufferedImage myPicture = ImageIO.read(new File(path));
        picLabel = new JLabel(new ImageIcon(myPicture));
        picLabel.repaint();
    }

    private void initObjects() {

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
