package pl.polsl.reservationsdatabasebean.controllers.impl;

import pl.polsl.reservationsdatabasebean.entities.ReservationTypes;
import pl.polsl.reservationsdatabasebean.controllers.ReservationTypesFacadeRemote;

import javax.ejb.Stateful;
import javax.naming.NamingException;

/**
 * @author matis
 */
@Stateful(mappedName = "ReservationTypesFacade")
public class ReservationTypesFacade extends AbstractFacade<ReservationTypes> implements ReservationTypesFacadeRemote {

    private static final long serialVersionUID = -3961133009017351835L;

    public ReservationTypesFacade() throws NamingException {
        super(ReservationTypes.class);
    }

}
