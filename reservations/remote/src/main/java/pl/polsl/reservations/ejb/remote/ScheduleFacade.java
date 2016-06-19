package pl.polsl.reservations.ejb.remote;

import java.util.List;
import javax.ejb.Remote;
import pl.polsl.reservations.dto.ReservationDTO;
import pl.polsl.reservations.dto.ReservationTypeDTO;
import pl.polsl.reservations.dto.UnauthorizedAccessException;

/**
 * Created by Krzysztof Stręk on 2016-05-11.
 */
@Remote
public interface ScheduleFacade extends AbstractBusinessFacade {

    List<ReservationDTO> getRoomSchedule(int roomId, int year, boolean semester);

    List<ReservationDTO> getDetailedRoomSchedule(int roomId, int year, int week, boolean semester);

    List<ReservationDTO> getReservationsByUser(int userId);

    void createReservation(int roomId, int startTime, int endTime, int week, int year, boolean semester, int typeId, int userId);

    void removeReservation(int reservationId) throws UnauthorizedAccessException;

    List<ReservationTypeDTO> getReservationTypes();

    void removeReservationType(int id);

    void createReservationType(ReservationTypeDTO reservationType);

    void editReservationType(ReservationTypeDTO reservationType);

    public Boolean editReservation(ReservationDTO dTO, Integer year, Boolean semester, Integer week);
}
