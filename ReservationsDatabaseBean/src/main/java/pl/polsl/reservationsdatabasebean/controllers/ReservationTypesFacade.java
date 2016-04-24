package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import pl.polsl.reservationsdatabasebeanremote.database.ReservationTypes;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.ReservationTypesFacadeRemote;

/**
 *
 * @author matis
 */
@Stateful
public class ReservationTypesFacade extends AbstractFacade<ReservationTypes> implements ReservationTypesFacadeRemote {

    public ReservationTypesFacade() {
        super(ReservationTypes.class);
    }
    
}
