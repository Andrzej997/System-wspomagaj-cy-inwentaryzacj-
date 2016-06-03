package pl.polsl.reservations.client.mediators;

import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.LoginView;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.dto.UserDTO;
import pl.polsl.reservations.ejb.remote.UserFacade;

/**
 *
 * @author Paweł
 */
public class LoginMediator {

    private final UserFacade userFacade;
    private LoginView loginWindow;
    private UserDTO userDTO;

    public LoginMediator() {
        userFacade = (UserFacade) Lookup.getRemote("UserFacade");
    }

    public boolean getUserData(String userName, String password) {
        return userFacade.login(userName, password);
    }

    public LoginView createView(MainView parent) {
        loginWindow = new LoginView(parent, this);
        if (userFacade.getUserPrivilege() != 1) {
            loginWindow.getRegisterButton().setVisible(false);
        }
        return loginWindow;
    }

}