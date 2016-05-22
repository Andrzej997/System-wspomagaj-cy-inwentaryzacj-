package pl.polsl.reservations.ejb.dao.impl;

import pl.polsl.reservations.ejb.dao.RoomDao;
import pl.polsl.reservations.ejb.dao.RoomTypesDao;
import pl.polsl.reservations.entities.Room;
import pl.polsl.reservations.entities.RoomTypes;
import pl.polsl.reservations.interceptors.TransactionalInterceptor;
import pl.polsl.reservations.logger.LoggerImpl;

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
    }
}
