package pl.polsl.reservations.schedule;

import java.util.List;
import pl.polsl.reservations.builder.DTOBuilder;
import pl.polsl.reservations.dto.ReservationDTO;
import pl.polsl.reservations.ejb.dao.ReservationsDao;
import pl.polsl.reservations.ejb.dao.RoomDao;
import pl.polsl.reservations.ejb.dao.RoomScheduleDao;
import pl.polsl.reservations.entities.Reservations;
import pl.polsl.reservations.entities.Room;
import pl.polsl.reservations.entities.RoomSchedule;

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
            ReservationsDao reservationsDAO,
            RoomScheduleDao roomScheduleDAO,
            RoomDao roomDAO) {

        List<ReservationDTO> result = decoratedStrategy.createSchedule(
                roomId,
                year,
                semester,
                reservationsDAO,
                roomScheduleDAO,
                roomDAO);

        Room room = roomDAO.find(roomId);
        RoomSchedule roomSchedule = roomScheduleDAO.getCurrentDateSchedule(year, week, semester, room);
        List<Reservations> reservationsList = reservationsDAO.getAllReservationsByRoomSchedule(roomSchedule);

        for (Reservations r : reservationsList) {
            result.add(DTOBuilder.buildReservationDTO(r));
        }
        return result;
    }
}
