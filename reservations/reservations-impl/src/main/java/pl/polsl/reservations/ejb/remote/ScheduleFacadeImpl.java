package pl.polsl.reservations.ejb.remote;

import pl.polsl.reservations.dto.ReservationDTO;
import pl.polsl.reservations.ejb.dao.*;
import pl.polsl.reservations.entities.Reservations;
import pl.polsl.reservations.entities.Room;
import pl.polsl.reservations.entities.RoomSchedule;
import pl.polsl.reservations.logger.LoggerImpl;
import pl.polsl.reservations.schedule.DetailedScheduleStrategyDecorator;
import pl.polsl.reservations.schedule.RoomScheduleStrategy;
import pl.polsl.reservations.schedule.ScheduleFactory;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-05-11.
 */
@Stateful(mappedName = "ScheduleFacade")
@Interceptors({LoggerImpl.class})
public class ScheduleFacadeImpl implements ScheduleFacade {

    @EJB
    ScheduleFactory scheduleFactory;

    @EJB
    ReservationsDao reservationsDAO;

    @EJB
    RoomDao roomDAO;

    @EJB
    RoomScheduleDao roomScheduleDAO;

    @EJB
    ReservationTypesDao reservationTypeDAO;

    @EJB
    UsersDao usersDAO;

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

        newReservaton.setRoomSchedule(schedule);
        newReservaton.setStartTime(startTime);
        newReservaton.setEndTime(endTime);
        newReservaton.setReservationType(reservationTypeDAO.find(typeId));
        newReservaton.setUserId(usersDAO.find(userId));

        reservationsDAO.create(newReservaton);
    }

    @Override
    public void removeReservation(int reservationId) {
        reservationsDAO.remove(reservationsDAO.find(reservationId));
    }

    @Override
    public List<ReservationDTO> getRoomSchedule(int roomId, int year, boolean semester) {
        return scheduleFactory.createSchedule(new RoomScheduleStrategy(), roomId, year, semester);
    }
}
