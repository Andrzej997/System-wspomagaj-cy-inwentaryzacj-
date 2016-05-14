package pl.polsl.reservations.schedule;

import pl.polsl.reservations.dto.ReservationDTO;
import pl.polsl.reservationsdatabasebeanremote.database.Reservations;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.RoomSchedule;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.ReservationsFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomScheduleFacadeRemote;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-05-12.
 */
public class RoomScheduleStrategy implements ScheduleStrategy {

    @Override
    public List<ReservationDTO> createSchedule(int roomId, int year, boolean semester,
                                               ReservationsFacadeRemote reservationsDAO,
                                               RoomScheduleFacadeRemote roomScheduleDAO,
                                               RoomFacadeRemote roomDAO) {
        List<ReservationDTO> result = new ArrayList<>();
        Room room = roomDAO.find(roomId);
        RoomSchedule roomSchedule = roomScheduleDAO.getCurrentDateSchedule(year, 0, false, room);
        List<Reservations> reservationsList = reservationsDAO.getAllReservationsByRoomSchedule(roomSchedule);

        for (Reservations r : reservationsList) {
            result.add(new ReservationDTO(r));
        }
        return result;
    }
}
