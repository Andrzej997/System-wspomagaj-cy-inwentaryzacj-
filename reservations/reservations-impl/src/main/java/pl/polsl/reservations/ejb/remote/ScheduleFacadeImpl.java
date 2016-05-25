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
import pl.polsl.reservations.annotations.PrivilegeLevel;
import pl.polsl.reservations.builder.DTOBuilder;
import pl.polsl.reservations.interceptors.PrivilegeInterceptor;

/**
 * Created by Krzysztof Stręk on 2016-05-11.
 */
@Stateful(mappedName = "ScheduleFacade")
@Interceptors({LoggerImpl.class, PrivilegeInterceptor.class})
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
    @PrivilegeLevel(privilegeLevel = "NONE")
    public List<ReservationDTO> getReservationsByUser(int userId) {
        List<ReservationDTO> result = new ArrayList<>();
        List<Reservations> reservationsList = reservationsDAO.getAllReservationsByUser((long)userId);
        for (Reservations r : reservationsList) {
            result.add(DTOBuilder.buildReservationDTO(r));
        }
        return result;
    }

    @Override
    @PrivilegeLevel(privilegeLevel = "NONE")
    public List<ReservationDTO> getDetailedRoomSchedule(int roomId, int year, int week, boolean semester) {
        return scheduleFactory.createSchedule(
                new DetailedScheduleStrategyDecorator(week, new RoomScheduleStrategy()),
                roomId,
                year,
                semester
        );
    }

    @Override
    @PrivilegeLevel(privilegeLevel = "NONE")
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
            schedule.setYear(year);
        }

        newReservaton.setRoomSchedule(schedule);
        newReservaton.setStartTime(startTime);
        newReservaton.setEndTime(endTime);
        newReservaton.setReservationType(reservationTypeDAO.find(typeId));
        newReservaton.setUserId(usersDAO.find(userId));

        reservationsDAO.create(newReservaton);
        List<Reservations> reservationsCollection = schedule.getReservationsCollection();
        if(schedule.getId() != null){
            reservationsCollection.add(newReservaton);
            schedule.setReservationsCollection(reservationsCollection);
            roomScheduleDAO.merge(schedule);
        } else {
            reservationsCollection = new ArrayList<>();
            reservationsCollection.add(newReservaton);
            schedule.setReservationsCollection(reservationsCollection);
            roomScheduleDAO.create(schedule);
        }
    }

    @Override
    @PrivilegeLevel(privilegeLevel = "NONE")
    public void removeReservation(int reservationId) {
        reservationsDAO.remove(reservationsDAO.find(reservationId));
    }

    @Override
    @PrivilegeLevel(privilegeLevel = "P_SCHEDULE_LOOKUP")
    public List<ReservationDTO> getRoomSchedule(int roomId, int year, boolean semester) {
        return scheduleFactory.createSchedule(new RoomScheduleStrategy(), roomId, year, semester);
    }
}