package pl.polsl.reservations.ejb.dao;

import java.util.List;
import javax.ejb.Local;

import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Equipment;
import pl.polsl.reservations.entities.Room;
import pl.polsl.reservations.entities.RoomSchedule;
import pl.polsl.reservations.entities.Workers;

/**
 * @author matis
 */
@Local
public interface RoomDao extends AbstractDao<Room> {

    Room getRoomByNumber(int number);

    List<Workers> getWorkersCollectionById(Number id);

    List<Equipment> getEquipmentCollectionById(Number id);

    List<RoomSchedule> getRoomScheduleCollectionById(Number id);

    List<Room> getRoomWithNumOfSeatsHigherOrEqualThan(Number numberOfSeats);

    Room getRoomByKeeper(Workers worker);
}
