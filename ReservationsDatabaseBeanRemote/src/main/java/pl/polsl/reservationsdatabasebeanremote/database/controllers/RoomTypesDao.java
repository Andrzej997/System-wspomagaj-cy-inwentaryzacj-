package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.RoomTypes;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author matis
 */
@Remote
public interface RoomTypesDao extends AbstractDao<RoomTypes> {

    List<Room> getRoomCollectionById(Number id);
}
