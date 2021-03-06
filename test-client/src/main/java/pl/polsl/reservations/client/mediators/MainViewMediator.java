package pl.polsl.reservations.client.mediators;

import java.awt.event.ActionEvent;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.dto.UserDTO;
import pl.polsl.reservations.ejb.remote.UserFacade;

/**
 *
 * @author matis
 */
public class MainViewMediator {

    private MainView mainView;
    private final UserFacade userFacade = Lookup.getUserFacade();

    public MainViewMediator() {
    }

    public MainView createView() {
        mainView = new MainView(this);
        mainView.create();
        return mainView;
    }

    public void dispatchLogoutMenuItemActionPerformed(ActionEvent evt) {
        userFacade.logout();
    }

    public String getUserDepartament() {
        UserDTO userDetails = userFacade.getUserDetails();
        return userDetails.getDepartment();
    }

    public Integer getUserRoomNumber() {
        UserDTO userDetails = userFacade.getUserDetails();
        return userDetails.getRoomNumber();
    }

}
