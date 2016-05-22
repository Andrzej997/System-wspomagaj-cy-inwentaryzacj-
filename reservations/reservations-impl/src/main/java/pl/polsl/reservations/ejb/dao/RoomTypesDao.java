package pl.polsl.reservations.ejb.dao;

import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Room;
import pl.polsl.reservations.entities.RoomTypes;

import javax.ejb.Local;
import java.util.List;

/**
 * @author matis
 */
@Local
public interface RoomTypesDao extends AbstractDao<RoomTypes> {

    List<Room> getRoomCollectionById(Number id);
}
