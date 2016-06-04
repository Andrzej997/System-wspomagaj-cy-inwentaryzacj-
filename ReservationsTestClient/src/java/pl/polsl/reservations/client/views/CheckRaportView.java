package pl.polsl.reservations.client.views;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 *
 * @author abienioszek
 */
class CheckRaportView extends JPanel {

    private static final long serialVersionUID = -8486774124937326659L;

    private MainView window;

    public CheckRaportView(MainView window) {
        this.window = window;
        initializeComponents();
        JOptionPane.showMessageDialog(null, "Not supported yet");
    }
    
    private void initializeComponents(){
        keyInputDispatcher();
    }

    private void keyInputDispatcher() {

        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        AbstractAction escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CheckRaportView.this.getWindow().dispose();
                System.exit(0);
            }
        };
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        actionMap.put("escape", escapeAction);
    }
    
    public MainView getWindow(){
        return window;
    }
    
    public void setWindow(MainView window){
        this.window = window;
    }

}
