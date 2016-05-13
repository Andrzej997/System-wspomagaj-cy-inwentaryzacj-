package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.persistence.Query;
import pl.polsl.reservationsdatabasebean.logger.LoggerImpl;
import pl.polsl.reservationsdatabasebeanremote.database.*;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.*;

import java.util.List;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class})
@Stateful
public class RoomFacade extends AbstractFacade<Room> implements RoomFacadeRemote {

    private static final long serialVersionUID = 3846358829048447657L;

    private EquipmentFacadeRemote equipmentFacadeRemote;

    private WorkersFacadeRemote workersFacadeRemote;

    private DepartamentsFacadeRemote departamentsFacadeRemote;

    private RoomScheduleFacadeRemote roomScheduleFacadeRemote;

    private RoomTypesFacadeRemote roomTypesFacadeRemote;

    public RoomFacade() throws NamingException {
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
    public void remove(Object id){
        getDependencies();

        Room room = this.find(id);
        List<Equipment> equipmentCollection = room.getEquipmentCollection();
        for(Equipment equipment : equipmentCollection){
            equipmentFacadeRemote.remove(equipment.getId());
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
            roomScheduleFacadeRemote.remove(roomSchedule.getId());
        }

        RoomTypes roomType = room.getRoomType();
        roomCollection = roomType.getRoomCollection();
        roomCollection.remove(room);
        roomType.setRoomCollection(roomCollection);
        roomTypesFacadeRemote.merge(roomType);

        super.remove(room.getId());
    }

    protected void getDependencies(){
        try {
            equipmentFacadeRemote = new EquipmentFacade();
            workersFacadeRemote = new WorkersFacade();
            departamentsFacadeRemote = new DepartamentsFacade();
            roomScheduleFacadeRemote = new RoomScheduleFacade();
            roomTypesFacadeRemote = new RoomTypesFacade();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        Integer priviligeLevel = this.getPriviligeContext().getPriviligeLevel();
        equipmentFacadeRemote.setPriviligeLevel(priviligeLevel);
        workersFacadeRemote.setPriviligeLevel(priviligeLevel);
        departamentsFacadeRemote.setPriviligeLevel(priviligeLevel);
        roomScheduleFacadeRemote.setPriviligeLevel(priviligeLevel);
        roomTypesFacadeRemote.setPriviligeLevel(priviligeLevel);
    }
}
