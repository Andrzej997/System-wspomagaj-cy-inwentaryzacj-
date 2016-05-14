package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Reservations;
import pl.polsl.reservationsdatabasebeanremote.database.RoomSchedule;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author matis
 */
@Remote
public interface ReservationsFacadeRemote extends AbstractFacadeRemote<Reservations> {

    List<Reservations> getAllReservationsByRoomSchedule(RoomSchedule roomSchedule);

    List<Reservations> getAllWeekReservations(int week, int year);

    List<Reservations> getCurrentDateReservations();

    List<Reservations> getAllReservationsByType(Long typeId);

    List<Reservations> getAllReservationsByUser(Long userId);
}
