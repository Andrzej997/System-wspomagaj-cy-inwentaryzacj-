package pl.polsl.reservations.schedule;

import java.util.ArrayList;
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
 * Created by Krzysztof Stręk on 2016-05-12.
 */
public class RoomScheduleStrategy implements ScheduleStrategy {

    @Override
    public List<ReservationDTO> createSchedule(int roomNumber, int year, boolean semester,
            ReservationsDao reservationsDAO,
            RoomScheduleDao roomScheduleDAO,
            RoomDao roomDAO) {
        List<ReservationDTO> result = new ArrayList<>();
        Room room = roomDAO.getRoomByNumber(roomNumber);
        RoomSchedule roomSchedule = roomScheduleDAO.getCurrentDateSchedule(year, 0, semester, room);
        List<Reservations> reservationsList = reservationsDAO.getAllReservationsByRoomSchedule(roomSchedule);

        for (Reservations r : reservationsList) {
            result.add(DTOBuilder.buildReservationDTO(r));
        }
        return result;
    }
}
