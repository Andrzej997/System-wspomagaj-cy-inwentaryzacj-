package pl.polsl.reservations.schedule;

import pl.polsl.reservations.dto.ReservationDTO;
import pl.polsl.reservationsdatabasebeanremote.database.ReservationTypes;
import pl.polsl.reservationsdatabasebeanremote.database.Reservations;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.RoomSchedule;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.*;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-05-11.
 */
@Stateful(mappedName = "ScheduleFacade")
public class ScheduleFacadeImpl implements ScheduleFacade {

    @EJB
    ScheduleFactory scheduleFactory;

    @EJB
    ReservationsFacadeRemote reservationsDAO;

    @EJB
    RoomFacadeRemote roomDAO;

    @EJB
    RoomScheduleFacadeRemote roomScheduleDAO;

    @EJB
    ReservationTypesFacadeRemote reservationTypeDAO;

    @EJB
    UsersFacadeRemote usersDAO;

    public ScheduleFacadeImpl() {
    }

    @Override
    public List<ReservationDTO> getReservationsByUser(int userId) {
        List<ReservationDTO> result = new ArrayList<>();
        List<Reservations> reservationsList = reservationsDAO.getAllReservationsByUser((long)userId);
        for (Reservations r : reservationsList) {
            result.add(new ReservationDTO(r));
        }
        return result;
    }

    @Override
    public List<ReservationDTO> getDetailedRoomSchedule(int roomId, int year, int week, boolean semester) {
        return scheduleFactory.createSchedule(
                new DetailedScheduleStrategyDecorator(week, new RoomScheduleStrategy()),
                roomId,
                year,
                semester
        );
    }

    @Override
    public void createReservation(int roomId, int startTime, int endTime, int week, int year, boolean semester, int typeId, int userId) {
        Reservations newReservaton = new Reservations();
        List<RoomSchedule> roomSchedules = roomScheduleDAO.getAllSchedulesByYearAndSemester(year, semester);

        RoomSchedule schedule = null;
        for (RoomSchedule r: roomSchedules) {
            if (r.getWeek() == week && r.getRoom().getId() == roomId) {
                schedule = r;
                break;
            }
        }

        if (schedule == null) {
            schedule = new RoomSchedule();
            Room room = roomDAO.find(roomId);
            schedule.setRoom(room);
            schedule.setWeek(week);
            schedule.setSemester(semester);
            schedule.set_year(new java.sql.Date(year-1900, 0, 1));
        }

        newReservaton.setRoomNumber(schedule);
        newReservaton.setStartTime(startTime);
        newReservaton.setEndTime(endTime);
        newReservaton.setReservationType(reservationTypeDAO.find(typeId));
        newReservaton.setUserId(usersDAO.find(userId));

        reservationsDAO.create(newReservaton);
    }

    @Override
    public void removeReservation(int reservationId) {
        reservationsDAO.remove(reservationId);
    }

    @Override
    public List<ReservationDTO> getRoomSchedule(int roomId, int year, boolean semester) {
        return scheduleFactory.createSchedule(new RoomScheduleStrategy(), roomId, year, semester);
    }
}
