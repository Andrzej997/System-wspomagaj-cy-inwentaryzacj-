package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.RoomTypes;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author matis
 */
@Remote
public interface RoomTypesFacadeRemote {

    void setPriviligeLevel(Integer level);

    void create(RoomTypes roomTypes);

    void edit(RoomTypes roomTypes);

    void remove(RoomTypes entity);

    void merge(RoomTypes roomTypes);

    RoomTypes find(Object id);

    RoomTypes getReference(Object id);

    List<RoomTypes> findAll();

    List<RoomTypes> findRange(int[] range);

    int count();

    List<RoomTypes> findEntity(List<String> columnNames, List<Object> values);

    List<Room> getRoomCollectionById(Number id);
}
