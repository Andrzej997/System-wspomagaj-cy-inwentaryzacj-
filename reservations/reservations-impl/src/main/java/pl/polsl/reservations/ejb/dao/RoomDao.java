package pl.polsl.reservations.ejb.dao;

import java.util.List;
import javax.ejb.Local;

import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Equipment;
import pl.polsl.reservations.entities.Room;
import pl.polsl.reservations.entities.RoomSchedule;
import pl.polsl.reservations.entities.Workers;

/**
 * @author Mateusz Sojka
 * @version 1.0
 *
 * Rooms data access object interface
 */
@Local
public interface RoomDao extends AbstractDao<Room> {

    /**
     * Method to get room by his number
     *
     * @param number int room number
     * @return Room entity
     */
    Room getRoomByNumber(int number);

    /**
     * Method to get room workers by id
     *
     * @param id room id
     * @return List of room workers
     */
    List<Workers> getWorkersCollectionById(Number id);

    /**
     * Method to get equipment collection by room id
     *
     * @param id room id
     * @return list of equipment
     */
    List<Equipment> getEquipmentCollectionById(Number id);

    /**
     * Method to get room schedule collection of room
     *
     * @param id room id
     * @return list of room schedules
     */
    List<RoomSchedule> getRoomScheduleCollectionById(Number id);

    /**
     * Method to get room which has higher seats quantity than given value
     *
     * @param numberOfSeats minimal seats quantity
     * @return List of rooms
     */
    List<Room> getRoomWithNumOfSeatsHigherOrEqualThan(Number numberOfSeats);

    /**
     * Method to get room by his keeper
     *
     * @param worker Workers entity
     * @return Room enitity
     */
    Room getRoomByKeeper(Workers worker);
}
