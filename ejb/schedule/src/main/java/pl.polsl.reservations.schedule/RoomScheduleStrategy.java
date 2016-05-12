package pl.polsl.reservations.schedule;

import pl.polsl.reservationsdatabasebeanremote.database.Reservations;
import pl.polsl.reservationsdatabasebeanremote.database.RoomSchedule;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.ReservationsFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomScheduleFacadeRemote;

import javax.ejb.EJB;
import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-05-12.
 */
public class RoomScheduleStrategy implements ScheduleStrategy {

    @EJB
    RoomScheduleFacadeRemote roomScheduleDAO;

    @EJB
    ReservationsFacadeRemote reservationsDAO;

    @Override
    public void createSchedule(int roomId) {
        RoomSchedule roomSchedule = roomScheduleDAO.getCurrentDateSchedule(2016, 0);
        List<Reservations> reservationsList = reservationsDAO.getAllReservationsByRoomSchedule(roomSchedule);
    }
}
