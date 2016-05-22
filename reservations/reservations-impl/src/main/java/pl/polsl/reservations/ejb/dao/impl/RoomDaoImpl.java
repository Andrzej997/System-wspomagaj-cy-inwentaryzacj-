package pl.polsl.reservations.ejb.dao.impl;

import pl.polsl.reservations.ejb.dao.*;
import pl.polsl.reservations.entities.*;
import pl.polsl.reservations.interceptors.TransactionalInterceptor;
import pl.polsl.reservations.logger.LoggerImpl;

import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.persistence.Query;
import java.util.List;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class, TransactionalInterceptor.class})
@Stateful
@TransactionManagement(value = TransactionManagementType.BEAN)
public class RoomDaoImpl extends AbstractDaoImpl<Room> implements RoomDao {

    private static final long serialVersionUID = 3846358829048447657L;

    private EquipmentDao equipmentFacadeRemote;

    private WorkersDao workersFacadeRemote;

    private DepartamentsDao departamentsFacadeRemote;

    private RoomScheduleDao roomScheduleFacadeRemote;

    private RoomTypesDao roomTypesFacadeRemote;

    public RoomDaoImpl() throws NamingException {
        super(Room.class);
    }

    @Override
    public Room getRoomByNumber(int number){
        Query query = getEntityManager().createNamedQuery("getRoomByNumber", Room.class);
        query.setParameter("roomNumber", number);
        return (Room)query.getSingleResult();
    }

    @Override
    public List<Workers> getWorkersCollectionById(Number id){
        Room room = this.find(id);
        return room.getWorkerses();
    }

    @Override
    public List<Equipment> getEquipmentCollectionById(Number id){
        Room room = this.find(id);
        return room.getEquipmentCollection();
    }

    @Override
    public List<RoomSchedule> getRoomScheduleCollectionById(Number id){
        Room room = this.find(id);
        return room.getRoomScheduleCollection();
    }

    @Override
    public void remove(Room entity) {
        getDependencies();

        Room room = this.find(entity.getId());
        List<Equipment> equipmentCollection = room.getEquipmentCollection();
        for(Equipment equipment : equipmentCollection){
            equipmentFacadeRemote.remove(equipment);
        }

        Workers keeper = room.getKeeperId();
        List<Room> roomCollection = keeper.getRoomCollection();
        roomCollection.remove(room);
        keeper.setRoomCollection(roomCollection);
        workersFacadeRemote.merge(keeper);


        List<Workers> workerses = room.getWorkerses();
        for(Workers worker : workerses){
            worker.setRoom(null);
            workersFacadeRemote.merge(worker);
        }

        Departaments departament = room.getDepartamentId();
        roomCollection = departament.getRoomCollection();
        roomCollection.remove(room);
        departament.setRoomCollection(roomCollection);
        departamentsFacadeRemote.merge(departament);

        List<RoomSchedule> roomScheduleCollection = room.getRoomScheduleCollection();
        for(RoomSchedule roomSchedule : roomScheduleCollection){
            roomScheduleFacadeRemote.remove(roomSchedule);
        }

        RoomTypes roomType = room.getRoomType();
        roomCollection = roomType.getRoomCollection();
        roomCollection.remove(room);
        roomType.setRoomCollection(roomCollection);
        roomTypesFacadeRemote.merge(roomType);

        super.remove(room);
    }

    @Override
    protected void getDependencies(){
        try {
            equipmentFacadeRemote = new EquipmentDaoImpl();
            workersFacadeRemote = new WorkersDaoImpl();
            departamentsFacadeRemote = new DepartamentsDaoImpl();
            roomScheduleFacadeRemote = new RoomScheduleDaoImpl();
            roomTypesFacadeRemote = new RoomTypesDaoImpl();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
