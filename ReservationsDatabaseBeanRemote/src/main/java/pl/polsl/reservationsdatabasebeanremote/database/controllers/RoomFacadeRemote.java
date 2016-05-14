package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Equipment;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.RoomSchedule;
import pl.polsl.reservationsdatabasebeanremote.database.Workers;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author matis
 */
@Remote
public interface RoomFacadeRemote extends AbstractFacadeRemote<Room> {

    Room getRoomByNumber(int number);

    List<Workers> getWorkersCollectionById(Number id);

    List<Equipment> getEquipmentCollectionById(Number id);

    List<RoomSchedule> getRoomScheduleCollectionById(Number id);
}
