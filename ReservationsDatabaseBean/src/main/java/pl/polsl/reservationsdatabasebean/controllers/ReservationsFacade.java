package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import pl.polsl.reservationsdatabasebeanremote.database.Reservations;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.ReservationsFacadeRemote;

/**
 *
 * @author matis
 */
@Stateful
public class ReservationsFacade extends AbstractFacade<Reservations> implements ReservationsFacadeRemote {

    public ReservationsFacade() {
        super(Reservations.class);
    }
    
}
