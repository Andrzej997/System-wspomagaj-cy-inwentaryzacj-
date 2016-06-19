package pl.polsl.reservations.ejb.dao;

import java.util.List;
import javax.ejb.Local;
import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Reservations;
import pl.polsl.reservations.entities.Room;
import pl.polsl.reservations.entities.RoomSchedule;

/**
 * @author matis
 */
@Local
public interface RoomScheduleDao extends AbstractDao<RoomSchedule> {

    List<RoomSchedule> getAllSchedulesByYearAndSemester(int year, boolean semester);

    List<RoomSchedule> getAllSchedulesAtSession(int year, boolean semester);

    RoomSchedule getCurrentDateSchedule(int year, int week, boolean semester, Room room);

    RoomSchedule getCurrentScheduleForRoom(int roomNumber);

    List<Reservations> getReservationsCollectionById(Number id);
}
