package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Reservations;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.RoomSchedule;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author matis
 */
@Remote
public interface RoomScheduleDao extends AbstractDao<RoomSchedule> {

    List<RoomSchedule> getAllSchedulesByYearAndSemester(int year, boolean semester);

    List<RoomSchedule> getAllSchedulesAtSession(int year, boolean semester);

    RoomSchedule getCurrentDateSchedule(int year, int week,boolean semester, Room room);

    RoomSchedule getCurrentScheduleForRoom(int roomNumber);

    List<Reservations> getReservationsCollectionById(Number id);
}
