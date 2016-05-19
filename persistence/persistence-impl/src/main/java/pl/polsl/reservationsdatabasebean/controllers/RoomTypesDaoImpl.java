package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.PrivilegeLevelEnum;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.RoomTypes;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomDao;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomTypesDao;
import pl.polsl.reservationsdatabasebeanremote.database.interceptors.TransactionalInterceptor;
import pl.polsl.reservationsdatabasebeanremote.database.logger.LoggerImpl;

import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import java.util.List;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class, TransactionalInterceptor.class})
@Stateful
@TransactionManagement(value = TransactionManagementType.BEAN)
public class RoomTypesDaoImpl extends AbstractDaoImpl<RoomTypes> implements RoomTypesDao {

    private static final long serialVersionUID = 3614381092644979715L;

    private RoomDao roomFacadeRemote;

    public RoomTypesDaoImpl() throws NamingException {
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

    @Override
    protected void getDependencies(){
        try {
            roomFacadeRemote = new RoomDaoImpl();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        PrivilegeLevelEnum privilegeLevel = this.getPriviligeLevel();
        roomFacadeRemote.setPriviligeLevel(privilegeLevel);
    }
}
