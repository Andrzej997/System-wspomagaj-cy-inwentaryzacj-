package pl.polsl.reservations.ejb.dao;

import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Equipment;
import pl.polsl.reservations.entities.Room;
import pl.polsl.reservations.entities.RoomSchedule;
import pl.polsl.reservations.entities.Workers;

import javax.ejb.Local;
import java.util.List;

/**
 * @author matis
 */
@Local
public interface RoomDao extends AbstractDao<Room> {

    Room getRoomByNumber(int number);

    List<Workers> getWorkersCollectionById(Number id);

    List<Equipment> getEquipmentCollectionById(Number id);

    List<RoomSchedule> getRoomScheduleCollectionById(Number id);
}
