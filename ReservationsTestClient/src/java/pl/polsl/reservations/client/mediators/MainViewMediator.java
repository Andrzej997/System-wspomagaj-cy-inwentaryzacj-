package pl.polsl.reservations.client.mediators;

import java.awt.event.ActionEvent;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.user.UserFacade;

/**
 *
 * @author matis
 */
public class MainViewMediator {
    
    private MainView mainView;
    private final UserFacade userFacade;
    
    public MainViewMediator(){
        userFacade = (UserFacade) Lookup.getRemote("UserFacade");
    }
    
    public MainView createView(){
        mainView = new MainView(this);
        mainView.create();
        return mainView;
    }
    
    public void dispatchLogoutMenuItemActionPerformed(ActionEvent evt){
        userFacade.logout();
    }
    
    
}
