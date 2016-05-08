package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebean.logger.LoggerImpl;
import pl.polsl.reservationsdatabasebeanremote.database.Reservations;
import pl.polsl.reservationsdatabasebeanremote.database.RoomSchedule;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.ReservationsFacadeRemote;

import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.List;

/**
 * @author matis
 */
//@Interceptors({LoggerImpl.class})
@Stateful
public class ReservationsFacade extends AbstractFacade<Reservations> implements ReservationsFacadeRemote {

    private static final long serialVersionUID = 8947630232538216657L;

    public ReservationsFacade() throws NamingException {
        super(Reservations.class);
    }

    @Override
    public List<Reservations> getAllReservationsByRoomSchedule(RoomSchedule roomSchedule){
        Query query = getEntityManager().createNamedQuery("getAllReservationsByRoomSchedule", Reservations.class);
        query.setParameter("roomSchedule", roomSchedule);
        return query.getResultList();
    }

    @Override
    public List<Reservations> getAllWeekReservations(int week, int year){
        Query query = getEntityManager().createNamedQuery("getAllWeekReservations", Reservations.class);
        Calendar c = Calendar.getInstance();
        c.set(year, 1, 1);
        query.setParameter("year", c.getTime());
        query.setParameter("week", week);
        return query.getResultList();
    }

    @Override
    public List<Reservations> getCurrentDateReservations(){
        Query query = getEntityManager().createNamedQuery("getAllWeekReservations", Reservations.class);
        Calendar c = Calendar.getInstance();
        query.setParameter("year", c.get(Calendar.YEAR));
        query.setParameter("week", c.get(Calendar.WEEK_OF_YEAR));
        return query.getResultList();
    }

    @Override
    public List<Reservations> getAllReservationsByType(Long typeId){
        Query query = getEntityManager().createNamedQuery("getAllReservationsByType", Reservations.class);
        query.setParameter("typeId", typeId);
        return query.getResultList();
    }

    @Override
    public List<Reservations> getAllReservationsByUser(Long userId){
        Query query = getEntityManager().createNamedQuery("getAllReservationsByUser", Reservations.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

}
