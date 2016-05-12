package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.persistence.Query;
import pl.polsl.reservationsdatabasebean.logger.LoggerImpl;
import pl.polsl.reservationsdatabasebeanremote.database.Equipment;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.RoomSchedule;
import pl.polsl.reservationsdatabasebeanremote.database.Workers;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomFacadeRemote;

import java.util.List;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class})
@Stateful
public class RoomFacade extends AbstractFacade<Room> implements RoomFacadeRemote {

    private static final long serialVersionUID = 3846358829048447657L;

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
}
