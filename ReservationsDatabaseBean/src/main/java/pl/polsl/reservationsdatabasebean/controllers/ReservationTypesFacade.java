package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import javax.naming.NamingException;
import pl.polsl.reservationsdatabasebeanremote.database.ReservationTypes;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.ReservationTypesFacadeRemote;

/**
 * @author matis
 */
//@Interceptors({LoggerImpl.class})
@Stateful
public class ReservationTypesFacade extends AbstractFacade<ReservationTypes> implements ReservationTypesFacadeRemote {

    private static final long serialVersionUID = -3961133009017351835L;

    public ReservationTypesFacade() throws NamingException{
        super(ReservationTypes.class);
    }

}
