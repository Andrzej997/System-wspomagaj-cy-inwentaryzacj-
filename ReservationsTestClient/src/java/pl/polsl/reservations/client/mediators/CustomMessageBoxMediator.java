package pl.polsl.reservations.client.mediators;

import pl.polsl.reservations.client.views.CustomMessageBox;
import pl.polsl.reservations.client.views.MainView;

/**
 *
 * @author matis
 */
public class CustomMessageBoxMediator {
    
    private CustomMessageBox messageBox;
    
    public CustomMessageBoxMediator(){
        
    }
    
    public void createView(MainView parent){
        messageBox = new CustomMessageBox(parent, this);
    }
    
}
