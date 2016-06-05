package pl.polsl.reservations.client.views;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
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
    
    private final TutorialViewMediator tutorialViewMediator;

    public TutorialView(MainView window, TutorialViewMediator tutorialViewMediator) {
        this.window = window;
        this.tutorialViewMediator = tutorialViewMediator;
        
    }
    
    private void initComponents(){
        keyInputDispatcher();
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
