package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import java.util.List;
import javax.ejb.Remote;

import pl.polsl.reservationsdatabasebeanremote.database.Reservations;
import pl.polsl.reservationsdatabasebeanremote.database.RoomSchedule;

/**
 * @author matis
 */
@Remote
public interface RoomScheduleFacadeRemote {

    void setPriviligeLevel(Integer level);

    void create(RoomSchedule roomSchedule);

    void edit(RoomSchedule roomSchedule);

    void remove(Object id);

    void merge(RoomSchedule roomSchedule);

    RoomSchedule find(Object id);

    RoomSchedule getReference(Object id);

    List<RoomSchedule> findAll();

    List<RoomSchedule> findRange(int[] range);

    int count();

    public List<RoomSchedule> findEntity(List<String> columnNames, List<Object> values);

    List<RoomSchedule> getAllSchedulesByYearAndSemester(int year, boolean semester);

    List<RoomSchedule> getAllSchedulesAtSession(int year, boolean semester);

    RoomSchedule getCurrentDateSchedule(int year, int week);

    RoomSchedule getCurrentScheduleForRoom(int roomNumber);

    List<Reservations> getReservationsCollectionById(Number id);
}
