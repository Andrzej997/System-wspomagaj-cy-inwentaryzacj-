package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import pl.polsl.reservationsdatabasebeanremote.database.RoomSchedule;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomScheduleFacadeRemote;

/**
 *
 * @author matis
 */
@Stateful
public class RoomScheduleFacade extends AbstractFacade<RoomSchedule> implements RoomScheduleFacadeRemote {

    public RoomScheduleFacade() {
        super(RoomSchedule.class);
    }
    
}
