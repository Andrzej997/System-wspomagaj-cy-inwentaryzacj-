package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import pl.polsl.reservationsdatabasebean.logger.LoggerImpl;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.RoomTypes;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomTypesFacadeRemote;

import java.util.List;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class})
@Stateful
public class RoomTypesFacade extends AbstractFacade<RoomTypes> implements RoomTypesFacadeRemote {

    private static final long serialVersionUID = 3614381092644979715L;

    private RoomFacadeRemote roomFacadeRemote;

    public RoomTypesFacade() throws NamingException {
        super(RoomTypes.class);
    }

    @Override
    public List<Room> getRoomCollectionById(Number id){
        RoomTypes roomTypes = this.find(id);
        return roomTypes.getRoomCollection();
    }

    @Override
    public void remove(Object id){
        getDependencies();

        RoomTypes roomTypes = this.find(id);
        List<Room> roomCollection = roomTypes.getRoomCollection();
        for(Room room : roomCollection){
            roomFacadeRemote.remove(room.getId());
        }

        super.remove(roomTypes.getRoomType());
    }


    protected void getDependencies(){
        try {
            roomFacadeRemote = new RoomFacade();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        Integer priviligeLevel = this.getPriviligeContext().getPriviligeLevel();
        roomFacadeRemote.remove(priviligeLevel);
    }
}
