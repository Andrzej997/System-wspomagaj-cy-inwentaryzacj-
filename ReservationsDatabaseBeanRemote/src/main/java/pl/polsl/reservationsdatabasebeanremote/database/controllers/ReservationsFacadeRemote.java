package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import java.util.List;
import javax.ejb.Remote;
import pl.polsl.reservationsdatabasebeanremote.database.Reservations;
import pl.polsl.reservationsdatabasebeanremote.database.RoomSchedule;

/**
 * @author matis
 */
@Remote
public interface ReservationsFacadeRemote {

    void setPriviligeLevel(Integer level);

    void create(Reservations reservations);

    void edit(Reservations reservations);

    void remove(Reservations reservations);

    void merge(Reservations reservations);

    Reservations find(Object id);

    Reservations getReference(Object id);

    List<Reservations> findAll();

    List<Reservations> findRange(int[] range);

    int count();

    public List<Reservations> findEntity(List<String> columnNames, List<Object> values);

    List<Reservations> getAllReservationsByRoomSchedule(RoomSchedule roomSchedule);

    List<Reservations> getAllWeekReservations(int week, int year);

    List<Reservations> getCurrentDateReservations();

    List<Reservations> getAllReservationsByType(Long typeId);

    List<Reservations> getAllReservationsByUser(Long userId);
}
