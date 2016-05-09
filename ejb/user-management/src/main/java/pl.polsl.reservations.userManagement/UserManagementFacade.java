package pl.polsl.reservations.userManagement;


import pl.polsl.reservationsdatabasebeanremote.database.controllers.ReservationsFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomFacadeRemote;

import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 * Created by Krzysztof Stręk on 2016-05-09.
 */
@Stateful(mappedName = "UserManagementFacade")
public class UserManagementFacade implements UserManagementFacadeRemote {

    @EJB
    ReservationsFacadeRemote reservation;

    @Override
    public int testMethod() {
        return 789987;
    }
}
