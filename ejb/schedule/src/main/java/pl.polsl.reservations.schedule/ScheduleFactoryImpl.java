package pl.polsl.reservations.schedule;

import pl.polsl.reservations.dto.ReservationDTO;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.ReservationsFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomScheduleFacadeRemote;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-05-12.
 */

@Stateful
public class ScheduleFactoryImpl implements ScheduleFactory {

    @EJB
    ReservationsFacadeRemote reservationsDAO;

    @EJB
    RoomScheduleFacadeRemote roomScheduleDAO;

    @EJB
    RoomFacadeRemote roomDAO;

    public ScheduleFactoryImpl() {
    }

    @Override
    public List<ReservationDTO> createSchedule(ScheduleStrategy strategy, int roomId, int year, boolean semester) {
        return strategy.createSchedule(roomId, year, semester, reservationsDAO, roomScheduleDAO, roomDAO);
    }
}
