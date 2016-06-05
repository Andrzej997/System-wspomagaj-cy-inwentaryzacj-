package pl.polsl.reservations.client.mediators;

import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.ChangePasswordView;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.ejb.remote.UserFacade;

/**
 *
 * @author matis
 */
public class ChangePasswordViewMediator {

    private ChangePasswordView changePasswordView;
    private final UserFacade userFacade;

    public ChangePasswordViewMediator() {
        userFacade = (UserFacade) Lookup.getRemote("UserFacade");
    }

    public ChangePasswordView createView(MainView mainView) {
        changePasswordView = new ChangePasswordView(mainView, this);

        return changePasswordView;
    }

    public Boolean onChangePassword() {
        String oldPassword = changePasswordView.getOldTf().getText();
        String newPassword = changePasswordView.getNew1Tf().getText();
        return userFacade.changePassword(oldPassword, newPassword);
    }

}
