package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.RoomSchedule;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomScheduleFacadeRemote;

import javax.ejb.Stateful;
import javax.naming.NamingException;

/**
 * @author matis
 */
@Stateful(mappedName = "RoomScheduleFacade")
public class RoomScheduleFacade extends AbstractFacade<RoomSchedule> implements RoomScheduleFacadeRemote {

    private static final long serialVersionUID = -8439468008559137683L;

    public RoomScheduleFacade() throws NamingException {
        super(RoomSchedule.class);
    }

}
