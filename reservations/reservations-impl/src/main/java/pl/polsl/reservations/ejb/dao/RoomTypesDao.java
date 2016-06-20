package pl.polsl.reservations.ejb.dao;

import java.util.List;
import javax.ejb.Local;
import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Room;
import pl.polsl.reservations.entities.RoomTypes;

/**
 * @author Mateusz Sojka
 * @version 1.0
 *
 * RoomTypes data access object interface
 */
@Local
public interface RoomTypesDao extends AbstractDao<RoomTypes> {

    /**
     * Method to get rooms which has given room type
     *
     * @param id room type id
     * @return List of rooms
     */
    List<Room> getRoomCollectionById(Number id);
}
