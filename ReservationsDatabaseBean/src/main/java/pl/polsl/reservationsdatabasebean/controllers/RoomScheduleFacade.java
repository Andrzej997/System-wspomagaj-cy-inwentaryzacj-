package pl.polsl.reservationsdatabasebean.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.persistence.Query;
import pl.polsl.reservationsdatabasebean.logger.LoggerImpl;
import pl.polsl.reservationsdatabasebeanremote.database.Reservations;
import pl.polsl.reservationsdatabasebeanremote.database.RoomSchedule;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomScheduleFacadeRemote;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class})
@Stateful
public class RoomScheduleFacade extends AbstractFacade<RoomSchedule> implements RoomScheduleFacadeRemote {

    private static final long serialVersionUID = -8439468008559137683L;

    public RoomScheduleFacade() throws NamingException {
        super(RoomSchedule.class);
    }

    @Override
    public List<RoomSchedule> getAllSchedulesByYearAndSemester(int year, boolean semester){
        Query query = this.getEntityManager().createNamedQuery("getAllSchedulesByYearAndSemester", RoomSchedule.class);
        Calendar c = Calendar.getInstance();
        c.set(year, 1, 1);
        query.setParameter("year", c.getTime());
        query.setParameter("semester", semester);
        return query.getResultList();
    }

    @Override
    public List<RoomSchedule> getAllSchedulesAtSession(int year, boolean semester){
        Query query = this.getEntityManager().createNamedQuery("getAllSchedulesAtSession", RoomSchedule.class);
        Calendar c = Calendar.getInstance();
        c.set(year, 1, 1);
        query.setParameter("year", c.getTime());
        query.setParameter("semester", semester);
        return query.getResultList();
    }

    @Override
    public RoomSchedule getCurrentDateSchedule(int year, int week){
        Query query = this.getEntityManager().createNamedQuery("getCurrentDateSchedule", RoomSchedule.class);
        Calendar c = Calendar.getInstance();
        c.set(year, 1, 1);
        query.setParameter("year", c.getTime());
        query.setParameter("week", week);
        return (RoomSchedule) query.getSingleResult();
    }

    @Override
    public RoomSchedule getCurrentScheduleForRoom(int roomNumber){
        Query query = this.getEntityManager().createNamedQuery("getCurrentScheduleForRoom", RoomSchedule.class);
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        int week = c.get(Calendar.WEEK_OF_YEAR);
        int year = date.getYear();
        RoomSchedule currentDateSchedule = getCurrentDateSchedule(year, week);
        query.setParameter("roomNumber", roomNumber);
        query.setParameter("RoomSchedule", currentDateSchedule);
        return (RoomSchedule) query.getSingleResult();
    }

    @Override
    public List<Reservations> getReservationsCollectionById(Number id){
        RoomSchedule roomSchedule = this.find(id);
        return roomSchedule.getReservationsCollection();
    }
}
