package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import pl.polsl.reservationsdatabasebean.logger.LoggerImpl;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.RoomTypes;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomTypesFacadeRemote;

import java.util.List;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class})
@Stateful
public class RoomTypesFacade extends AbstractFacade<RoomTypes> implements RoomTypesFacadeRemote {

    private static final long serialVersionUID = 3614381092644979715L;

    public RoomTypesFacade() throws NamingException {
        super(RoomTypes.class);
    }

    @Override
    public List<Room> getRoomCollectionById(Number id){
        RoomTypes roomTypes = this.find(id);
        return roomTypes.getRoomCollection();
    }

}
