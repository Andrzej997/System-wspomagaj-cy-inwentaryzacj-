package pl.polsl.reservations.schedule;

import pl.polsl.reservations.dto.ReservationDTO;
import pl.polsl.reservations.ejb.dao.ReservationsDao;
import pl.polsl.reservations.ejb.dao.RoomDao;
import pl.polsl.reservations.ejb.dao.RoomScheduleDao;

import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-05-12.
 */
public interface ScheduleStrategy {
    List<ReservationDTO> createSchedule(int roomId, int year, boolean semester,
                                        ReservationsDao reservationsDAO,
                                        RoomScheduleDao roomScheduleDAO,
                                        RoomDao roomDAO);
}
