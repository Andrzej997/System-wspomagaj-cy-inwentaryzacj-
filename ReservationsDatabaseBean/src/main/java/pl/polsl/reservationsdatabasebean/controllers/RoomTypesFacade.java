package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebean.logger.LoggerImpl;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.RoomTypes;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomTypesFacadeRemote;

import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import java.util.List;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class})
@Stateful
@TransactionManagement(value = TransactionManagementType.BEAN)
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
    public void remove(RoomTypes entity) {
        getDependencies();

        RoomTypes roomTypes = this.find(entity);
        List<Room> roomCollection = roomTypes.getRoomCollection();
        for(Room room : roomCollection){
            roomFacadeRemote.remove(room);
        }

        super.remove(roomTypes);
    }


    protected void getDependencies(){
        try {
            roomFacadeRemote = new RoomFacade();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        Integer priviligeLevel = this.getPriviligeContext().getPriviligeLevel();
        roomFacadeRemote.setPriviligeLevel(priviligeLevel);
    }
}
