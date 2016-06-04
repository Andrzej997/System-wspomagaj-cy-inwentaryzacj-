package pl.polsl.reservations.client.mediators;

import java.util.List;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.LoginView;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.dto.RoomDTO;
import pl.polsl.reservations.dto.UserDTO;
import pl.polsl.reservations.ejb.remote.RoomManagementFacade;
import pl.polsl.reservations.ejb.remote.UserFacade;

/**
 *
 * @author Pawe³
 */
public class LoginMediator {

    private final UserFacade userFacade;
    private LoginView loginWindow;
    private final RoomManagementFacade roomManagementFacade = (RoomManagementFacade) Lookup.getRemote("RoomManagementFacade");
    private UserDTO userDTO;

    public LoginMediator() {
        userFacade = (UserFacade) Lookup.getRemote("UserFacade");
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
        if (userFacade.getUserPriviligeLevel().getPrivilegeLevel() != 1l) {
            loginWindow.getRegisterButton().setVisible(false);
        }
        return loginWindow;
    }

}
