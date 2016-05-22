package pl.polsl.reservations.ejb.dao;

import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Reservations;
import pl.polsl.reservations.entities.RoomSchedule;

import javax.ejb.Local;
import java.util.List;

/**
 * @author matis
 */
@Local
public interface ReservationsDao extends AbstractDao<Reservations> {

    List<Reservations> getAllReservationsByRoomSchedule(RoomSchedule roomSchedule);

    List<Reservations> getAllWeekReservations(int week, int year);

    List<Reservations> getCurrentDateReservations();

    List<Reservations> getAllReservationsByType(Long typeId);

    List<Reservations> getAllReservationsByUser(Long userId);
}
