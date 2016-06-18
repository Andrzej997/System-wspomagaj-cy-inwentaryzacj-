package pl.polsl.reservations.client.mediators;

import java.util.List;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.LoginView;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.dto.RoomDTO;
import pl.polsl.reservations.ejb.remote.RoomManagementFacade;
import pl.polsl.reservations.ejb.remote.UserFacade;

/**
 *
 * @author Pawe³
 */
public class LoginMediator {

    private final UserFacade userFacade = Lookup.getUserFacade();
    private LoginView loginWindow;
    private final RoomManagementFacade roomManagementFacade = Lookup.getRoomManagementFacade();

    public LoginMediator() {
    }

    public boolean getUserData(String userName, String password) {
        return userFacade.login(userName, password);
    }

    public int getFirstRoom() {
        List<RoomDTO> roomsList = roomManagementFacade.getRoomsList();

        return roomsList.get(0).getNumber();
    }
    public LoginView createView(MainView parent) {
        loginWindow = new LoginView(parent, this);
        return loginWindow;
    }
    
    public void loginAsGuest(){
        userFacade.loginAsGuest();
    }
}
