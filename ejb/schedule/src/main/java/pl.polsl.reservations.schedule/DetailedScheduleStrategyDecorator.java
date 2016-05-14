package pl.polsl.reservations.schedule;

import pl.polsl.reservations.dto.ReservationDTO;
import pl.polsl.reservationsdatabasebeanremote.database.Reservations;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.RoomSchedule;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.ReservationsFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomScheduleFacadeRemote;

import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-05-14.
 */
public class DetailedScheduleStrategyDecorator implements ScheduleStrategy {

    int week;

    ScheduleStrategy decoratedStrategy;

    public DetailedScheduleStrategyDecorator(int week, ScheduleStrategy decoratedStrategy) {
        this.week = week;
        this.decoratedStrategy = decoratedStrategy;
    }

    @Override
    public List<ReservationDTO> createSchedule(int roomId, int year, boolean semester,
                                               ReservationsFacadeRemote reservationsDAO,
                                               RoomScheduleFacadeRemote roomScheduleDAO,
                                               RoomFacadeRemote roomDAO) {

        List<ReservationDTO> result = decoratedStrategy.createSchedule(
                roomId,
                year,
                semester,
                reservationsDAO,
                roomScheduleDAO,
                roomDAO);

        Room room = roomDAO.find(roomId);
        RoomSchedule roomSchedule = roomScheduleDAO.getCurrentDateSchedule(year, week, false, room);
        List<Reservations> reservationsList = reservationsDAO.getAllReservationsByRoomSchedule(roomSchedule);

        for (Reservations r : reservationsList) {
            result.add(new ReservationDTO(r));
        }
        return result;
    }
}
