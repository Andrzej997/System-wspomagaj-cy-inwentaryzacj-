package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import pl.polsl.reservationsdatabasebeanremote.database.RoomTypes;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomTypesFacadeRemote;

/**
 *
 * @author matis
 */
@Stateful
public class RoomTypesFacade extends AbstractFacade<RoomTypes> implements RoomTypesFacadeRemote {

    public RoomTypesFacade() {
        super(RoomTypes.class);
    }
    
}
