package pl.polsl.reservations.ejb.remote;

import java.util.List;
import javax.ejb.Remote;
import pl.polsl.reservations.dto.ReservationDTO;
import pl.polsl.reservations.dto.ReservationTypeDTO;
import pl.polsl.reservations.dto.UnauthorizedAccessException;

/**
 * Created by Krzysztof Stręk on 2016-05-11.
 *
 * @version 1.0
 *
 * Schedule facade remote interface to manage room schedules
 */
@Remote
public interface ScheduleFacade extends AbstractBusinessFacade {

    /**
     * Method to get general room schedule
     *
     * @param roomId room id
     * @param year schedule year
     * @param semester schedule semester (true means summer)
     * @return List of reservations
     */
    List<ReservationDTO> getRoomSchedule(int roomId, int year, boolean semester);

    /**
     * Method to get detailed weekly room schedule
     *
     * @param roomId room id
     * @param year schedule year
     * @param week schedule week
     * @param semester schedule semester (true means summer)
     * @return List of reservations
     */
    List<ReservationDTO> getDetailedRoomSchedule(int roomId, int year, int week, boolean semester);

    /**
     * Method to get users' reservations
     *
     * @param userId user id
     * @return list of reservations made by given user
     */
    List<ReservationDTO> getReservationsByUser(int userId);

    /**
     * Method to create new reservation
     *
     * @param roomId room id
     * @param startTime integer with resrvation start time value 0 - 00:00
     * Monday, 1 - 00:15 Monday up to 671
     * @param endTime integer with resrvation end time value 0 - 00:00 Monday, 1
     * - 00:15 Monday up to 671
     * @param week reservation week
     * @param year reservation year
     * @param semester reservation semester (true means summer)
     * @param typeId reservation type id from dictionary
     * @param userId user id which made this reservation
     */
    void createReservation(int roomId, int startTime, int endTime, int week, int year, boolean semester, int typeId, int userId);

    /**
     * Method to remove reservation
     *
     * @param reservationId reservation id to remove
     * @throws UnauthorizedAccessException when user has no access to remove
     */
    void removeReservation(int reservationId) throws UnauthorizedAccessException;

    /**
     * Method to get reservation type dictionary values
     *
     * @return list of reservation types
     */
    List<ReservationTypeDTO> getReservationTypes();

    /**
     * Method to remove reservation type from dictionary
     *
     * @param id reservation type id
     */
    void removeReservationType(int id);

    /**
     * Method to add new reservation type to dictionary
     *
     * @param reservationType new reservation type data
     */
    void createReservationType(ReservationTypeDTO reservationType);

    /**
     * Method to edit reservation type data
     *
     * @param reservationType new reservation type data
     */
    void editReservationType(ReservationTypeDTO reservationType);

    /**
     * Method to edit existing reservation
     *
     * @param dTO reservation data
     * @param year reservation year
     * @param semester reservation semester (true means summer)
     * @param week reservation week
     * @return true if success
     */
    public Boolean editReservation(ReservationDTO dTO, Integer year, Boolean semester, Integer week);
}
