package pl.polsl.reservations.ejb.dao;

import java.util.List;
import javax.ejb.Local;
import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Room;
import pl.polsl.reservations.entities.RoomTypes;

/**
 * @author matis
 */
@Local
public interface RoomTypesDao extends AbstractDao<RoomTypes> {

    List<Room> getRoomCollectionById(Number id);
}
