package pl.polsl.reservations.ejb.dao;

import java.util.List;
import javax.ejb.Local;
import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Reservations;
import pl.polsl.reservations.entities.Room;
import pl.polsl.reservations.entities.RoomSchedule;

/**
 * @author Mateusz Sojka
 * @version 1.0
 *
 * RoomSchedule data access object interface
 */
@Local
public interface RoomScheduleDao extends AbstractDao<RoomSchedule> {

    /**
     * Method to get all schedules by year and semester
     *
     * @param year int schedule year
     * @param semester boolean schedule semester (true means summer)
     * @return list of room schedules
     */
    List<RoomSchedule> getAllSchedulesByYearAndSemester(int year, boolean semester);

    /**
     * Method to get all schedules by year and semester at session
     *
     * @param year int schedule year
     * @param semester boolean schedule semester (true means summer)
     * @return list of room schedules
     */
    List<RoomSchedule> getAllSchedulesAtSession(int year, boolean semester);

    /**
     * Method to get schedule
     *
     * @param year int schedule year
     * @param week int schedule week
     * @param semester boolean schedule semester (true means summer)
     * @param room Room entity
     * @return RoomSchedule entity
     */
    RoomSchedule getCurrentDateSchedule(int year, int week, boolean semester, Room room);

    /**
     * Method to get current date schedule
     *
     * @param roomNumber room number
     * @return RoomSchedule entity
     */
    RoomSchedule getCurrentScheduleForRoom(int roomNumber);

    /**
     * Method to get reservations collection by schedule id
     *
     * @param id schedule id
     * @return list of reservations
     */
    List<Reservations> getReservationsCollectionById(Number id);
}
