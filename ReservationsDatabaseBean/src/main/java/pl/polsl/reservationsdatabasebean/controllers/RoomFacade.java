package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomFacadeRemote;

/**
 *
 * @author matis
 */
@Stateful
public class RoomFacade extends AbstractFacade<Room> implements RoomFacadeRemote {
    
    public RoomFacade() {
        super(Room.class);
    }
    
}
