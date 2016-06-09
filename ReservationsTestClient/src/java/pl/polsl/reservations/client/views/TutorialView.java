package pl.polsl.reservations.client.views;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import pl.polsl.reservations.client.mediators.TutorialViewMediator;

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
            setupTutorial(counter);}
        catch(IOException e){
            System.out.println("RESOURCE ERROR: " + e.toString()); 
        }
        add(picLabel);
        keyInputDispatcher();
    }

    private void setupTutorial(int counter) throws IOException {
        String path = "";
        switch(counter){
            case 1:
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
