package pl.polsl.reservations.ejb.remote;

import pl.polsl.reservations.dto.ReservationDTO;

import javax.ejb.Remote;
import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-05-11.
 */
@Remote
public interface ScheduleFacade {
    List<ReservationDTO> getRoomSchedule(int roomId, int year, boolean semester);
    List<ReservationDTO> getDetailedRoomSchedule(int roomId, int year, int week, boolean semester);
    List<ReservationDTO> getReservationsByUser(int userId);
    void createReservation(int roomId, int startTime, int endTime, int week, int year, boolean semester, int typeId, int userId);
    void removeReservation(int reservationId);

    //TODO implementacja jak będzie wiadomo jak ma działać
    default void findReservation() {
    }
}
