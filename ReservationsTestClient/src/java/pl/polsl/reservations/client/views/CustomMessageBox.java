package pl.polsl.reservations.client.views;

import javax.swing.JPanel;
import pl.polsl.reservations.client.mediators.CustomMessageBoxMediator;

/**
 *
 * @author matis
 */
public class CustomMessageBox extends JPanel{

    private static final long serialVersionUID = 3624752643793124038L;
    
    private final MainView parent;
    private final CustomMessageBoxMediator mediator;
    
    public CustomMessageBox(MainView parent, CustomMessageBoxMediator mediator){
        this.parent = parent;
        this.mediator = mediator;
    }

    @Override
    public MainView getParent() {
        return parent;
    }
    
    
    
}
