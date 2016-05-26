package pl.polsl.reservations.schedule;

import java.util.List;
import javax.ejb.Local;
import pl.polsl.reservations.dto.ReservationDTO;

/**
 * Created by Krzysztof Stręk on 2016-05-13.
 */
@Local
public interface ScheduleFactory {
    void setUserContext(String certificate);
    List<ReservationDTO> createSchedule(ScheduleStrategy strategy, int roomId, int year, boolean semester);
}
