package pl.polsl.reservations.schedule;

import pl.polsl.reservations.dto.ReservationDTO;

import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-05-13.
 */
public interface ScheduleFactory {
    List<ReservationDTO> createSchedule(ScheduleStrategy strategy, int roomId, int year, boolean semester);
}
