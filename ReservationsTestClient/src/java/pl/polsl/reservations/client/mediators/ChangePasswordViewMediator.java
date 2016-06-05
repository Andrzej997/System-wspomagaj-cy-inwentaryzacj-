package pl.polsl.reservations.client.mediators;

import pl.polsl.reservations.client.views.ChangePasswordView;
import pl.polsl.reservations.client.views.MainView;

/**
 *
 * @author matis
 */
public class ChangePasswordViewMediator {
 
    private ChangePasswordView changePasswordView;
    
    public ChangePasswordViewMediator(){
        
    }
    
    public ChangePasswordView createView(MainView mainView){
        changePasswordView = new ChangePasswordView(mainView, this);
        
        return changePasswordView;
    }
    
}
