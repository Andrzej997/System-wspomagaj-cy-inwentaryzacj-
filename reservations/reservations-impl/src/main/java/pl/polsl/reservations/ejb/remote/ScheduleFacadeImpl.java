package pl.polsl.reservations.ejb.remote;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.interceptor.Interceptors;

import pl.polsl.reservations.annotations.RequiredPrivilege;
import pl.polsl.reservations.builder.DTOBuilder;
import pl.polsl.reservations.dto.ReservationDTO;
import pl.polsl.reservations.dto.ReservationTypeDTO;
import pl.polsl.reservations.dto.UnauthorizedAccessException;
import pl.polsl.reservations.ejb.dao.*;
import pl.polsl.reservations.ejb.local.UserContext;
import pl.polsl.reservations.entities.ReservationTypes;
import pl.polsl.reservations.entities.Reservations;
import pl.polsl.reservations.entities.Room;
import pl.polsl.reservations.entities.RoomSchedule;
import pl.polsl.reservations.entities.Users;
import pl.polsl.reservations.interceptors.PrivilegeInterceptor;
import pl.polsl.reservations.logger.LoggerImpl;
import pl.polsl.reservations.privileges.PrivilegeEnum;
import pl.polsl.reservations.schedule.DetailedScheduleStrategyDecorator;
import pl.polsl.reservations.schedule.RoomScheduleStrategy;
import pl.polsl.reservations.schedule.ScheduleFactory;

/**
 * Created by Krzysztof Stręk on 2016-05-11.
 */
@Stateful(mappedName = "ScheduleFacade")
@StatefulTimeout(value = 30)
@Interceptors({LoggerImpl.class, PrivilegeInterceptor.class})
public class ScheduleFacadeImpl extends AbstractBusinessFacadeImpl implements ScheduleFacade {

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
        super();
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.SCHEDULE_LOOKUP)
    public List<ReservationDTO> getReservationsByUser(int userId) {
        List<ReservationDTO> result = new ArrayList<>();
        List<Reservations> reservationsList = reservationsDAO.getAllReservationsByUser((long) userId);
        for (Reservations r : reservationsList) {
            result.add(DTOBuilder.buildReservationDTO(r));
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
    @RequiredPrivilege(PrivilegeEnum.ADD_RESERVATION)
    public void createReservation(int roomId, int startTime, int endTime, int week, int year, boolean semester, int typeId, int userId) {
        Reservations newReservaton = new Reservations();
        List<RoomSchedule> roomSchedules = roomScheduleDAO.getAllSchedulesByYearAndSemester(year, semester);

        RoomSchedule schedule = null;
        for (RoomSchedule r : roomSchedules) {
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
        newReservaton.setUser(usersDAO.find(userId));

        reservationsDAO.create(newReservaton);
        if (schedule.getId() != null) {
            List<Reservations> reservationsCollection = schedule.getReservationsCollection();
            reservationsCollection.add(newReservaton);
            schedule.setReservationsCollection(reservationsCollection);
            roomScheduleDAO.merge(schedule);
        } else {
            List<Reservations> reservationsCollection = new ArrayList<>();
            reservationsCollection.add(newReservaton);
            schedule.setReservationsCollection(reservationsCollection);
            roomScheduleDAO.create(schedule);
        }
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.MODIFY_RESERVATION_OWN)
    public Boolean editReservation(ReservationDTO dTO, Integer year, Boolean semester, Integer week) {
         RoomSchedule currentDateSchedule = null;
        if (dTO.getRoomNumber() != null) {
            Room room = roomDAO.getRoomByNumber(dTO.getRoomNumber());
            if (year != null && week != null && semester != null && room != null) {
                currentDateSchedule = roomScheduleDAO.getCurrentDateSchedule(year, week, semester, room);
            }
        }
        Reservations newReservation = reservationsDAO.find(dTO.getId());
        if (dTO.getEndTime() != null) {
            newReservation.setEndTime(dTO.getEndTime());
        }
        if (dTO.getStartTime() != null) {
            newReservation.setStartTime(dTO.getStartTime());
        }
        Users user = usersDAO.find(dTO.getUserId());
        if (user != null) {
            newReservation.setUser(user);
        }
        String type = dTO.getType();
        List<ReservationTypes> findAll = reservationTypeDAO.findAll();
        ReservationTypes reservationType = null;
        for (ReservationTypes types : findAll) {
            if (types.getTypeShortDescription().equals(type)) {
                reservationType = types;
            }
        }
        if (reservationType != null) {
            newReservation.setReservationType(reservationType);
        }
        if (currentDateSchedule != null) {
            newReservation.setRoomSchedule(currentDateSchedule);
        }
        reservationsDAO.edit(newReservation);
        return true;
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.MODIFY_RESERVATION_OWN)
    public void removeReservation(int reservationId) throws UnauthorizedAccessException {
        Reservations reservation = reservationsDAO.find(reservationId);
        UserContext userContext = getCurrentUserContext();
        Long userId = reservation.getUser().getId();
        if (userContext.checkPrivilege(PrivilegeEnum.MODIFY_RESERVATION_WORKER)
                || userId.equals(userContext.getUser().getId())) {
            reservationsDAO.remove(reservationsDAO.find(reservationId));
        } else {
            throw new UnauthorizedAccessException("No access to reservations of user with ID: " + userId);
        }

    }

    @Override
    public List<ReservationTypeDTO> getReservationTypes() {
        List<ReservationTypes> types = reservationTypeDAO.findAll();

        List<ReservationTypeDTO> result = new ArrayList<>();

        for (ReservationTypes r : types) {
            result.add(DTOBuilder.buildReservationTypeDTO(r));
        }

        return result;
    }

    @Override
    public void removeReservationType(int id) {
        reservationTypeDAO.remove(reservationTypeDAO.find(id));
    }

    @Override
    public void createReservationType(String shortDescription, String longDescription, String color) {
        ReservationTypes newType = new ReservationTypes();
        newType.setTypeShortDescription(shortDescription);
        newType.setTypeLongDescription(longDescription);
        newType.setReservationColor(color);
        reservationTypeDAO.create(newType);
    }

    @Override
    public List<ReservationDTO> getRoomSchedule(int roomId, int year, boolean semester) {
        return scheduleFactory.createSchedule(new RoomScheduleStrategy(), roomId, year, semester);
    }

    @Override
    public Boolean certificateBean(String certificate) {
        Boolean certificateBean = super.certificateBean(certificate);
        scheduleFactory.setUserContext(certificate);
        reservationsDAO.setUserContext(certificate);
        roomDAO.setUserContext(certificate);
        roomScheduleDAO.setUserContext(certificate);
        reservationTypeDAO.setUserContext(certificate);
        usersDAO.setUserContext(certificate);
        return certificateBean;
    }

}
