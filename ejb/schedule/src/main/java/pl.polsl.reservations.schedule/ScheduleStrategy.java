package pl.polsl.reservations.schedule;

import pl.polsl.reservations.dto.ReservationDTO;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.ReservationsFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomScheduleFacadeRemote;

import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-05-12.
 */
public interface ScheduleStrategy {
    List<ReservationDTO> createSchedule(int roomId, int year, boolean semester,
                                        ReservationsFacadeRemote reservationsDAO,
                                        RoomScheduleFacadeRemote roomScheduleDAO,
                                        RoomFacadeRemote roomDAO);
}
