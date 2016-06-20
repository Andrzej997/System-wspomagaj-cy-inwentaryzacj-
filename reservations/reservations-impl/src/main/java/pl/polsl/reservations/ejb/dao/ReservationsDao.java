package pl.polsl.reservations.ejb.dao;

import java.util.List;
import javax.ejb.Local;
import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Reservations;
import pl.polsl.reservations.entities.RoomSchedule;

/**
 * @author Mateusz Sojka
 * @version 1.0
 *
 * Reservation data access object interface
 */
@Local
public interface ReservationsDao extends AbstractDao<Reservations> {

    /**
     * Method to get all reservations from room schedule
     *
     * @param roomSchedule RoomSchedule entity
     * @return list of reservations
     */
    List<Reservations> getAllReservationsByRoomSchedule(RoomSchedule roomSchedule);

    /**
     * Method to get all week reservations
     *
     * @param week int reservation week
     * @param year int reservation year
     * @return list of reservations
     */
    List<Reservations> getAllWeekReservations(int week, int year);

    /**
     * Method to get current date reservations
     *
     * @return List of reservations
     */
    List<Reservations> getCurrentDateReservations();

    /**
     * Method to get all reservations by type
     *
     * @param typeId reservation type id
     * @return list of reservations
     */
    List<Reservations> getAllReservationsByType(Long typeId);

    /**
     * Method to get all reservations by user id
     *
     * @param userId user id
     * @return list of reservations
     */
    List<Reservations> getAllReservationsByUser(Long userId);
}
