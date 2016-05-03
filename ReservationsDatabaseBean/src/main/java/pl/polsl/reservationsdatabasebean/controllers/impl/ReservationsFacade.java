package pl.polsl.reservationsdatabasebean.controllers.impl;

import pl.polsl.reservationsdatabasebean.entities.Reservations;
import pl.polsl.reservationsdatabasebean.controllers.ReservationsFacadeRemote;

import javax.ejb.Stateful;
import javax.naming.NamingException;

/**
 * @author matis
 */
@Stateful(mappedName = "ReservationsFacade")
public class ReservationsFacade extends AbstractFacade<Reservations> implements ReservationsFacadeRemote {

    private static final long serialVersionUID = 8947630232538216657L;

    public ReservationsFacade() throws NamingException {
        super(Reservations.class);
    }

}
