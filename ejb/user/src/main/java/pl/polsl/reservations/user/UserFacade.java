package pl.polsl.reservations.user;

import pl.polsl.reservationsdatabasebeanremote.database.Reservations;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.ReservationsFacadeRemote;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-05-07.
 */
@Stateful(mappedName = "UserFacade")
public class UserFacade implements UserFacadeRemote {

    @EJB
    ReservationsFacadeRemote reservation;

    @Override
    public String getUser() {
        List<Reservations> list =  reservation.findAll();
        return "asdasd";
    }
}
