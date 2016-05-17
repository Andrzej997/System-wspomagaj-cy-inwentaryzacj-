package pl.polsl.reservations.schedule;

import pl.polsl.reservations.dto.ReservationDTO;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.ReservationsDao;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomDao;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomScheduleDao;

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
