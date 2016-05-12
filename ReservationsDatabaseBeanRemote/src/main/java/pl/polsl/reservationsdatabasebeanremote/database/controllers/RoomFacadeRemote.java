package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import java.util.List;
import javax.ejb.Remote;

import pl.polsl.reservationsdatabasebeanremote.database.Equipment;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.RoomSchedule;
import pl.polsl.reservationsdatabasebeanremote.database.Workers;

/**
 * @author matis
 */
@Remote
public interface RoomFacadeRemote {

    void setPriviligeLevel(Integer level);

    void create(Room room);

    void edit(Room room);

    void remove(Object id);

    void merge(Room room);

    Room find(Object id);

    Room getReference(Object id);

    List<Room> findAll();

    List<Room> findRange(int[] range);

    int count();

    public List<Room> findEntity(List<String> columnNames, List<Object> values);

    Room getRoomByNumber(int number);

    List<Workers> getWorkersCollectionById(Number id);

    List<Equipment> getEquipmentCollectionById(Number id);

    List<RoomSchedule> getRoomScheduleCollectionById(Number id);
}
