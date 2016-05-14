package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.ReservationTypes;
import pl.polsl.reservationsdatabasebeanremote.database.Reservations;
import pl.polsl.reservationsdatabasebeanremote.database.RoomSchedule;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.ReservationTypesFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.ReservationsFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomScheduleFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.interceptors.TransactionalInterceptor;
import pl.polsl.reservationsdatabasebeanremote.database.logger.LoggerImpl;

import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class, TransactionalInterceptor.class})
@Stateful
@TransactionManagement(value = TransactionManagementType.BEAN)
public class ReservationsFacade extends AbstractFacade<Reservations> implements ReservationsFacadeRemote {

    private static final long serialVersionUID = 8947630232538216657L;
    private RoomScheduleFacadeRemote roomScheduleFacadeRemote;
    private ReservationTypesFacadeRemote reservationTypesFacadeRemote;

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
        Date date = new java.sql.Date(year-1900, 0, 1);
        query.setParameter("year", date);
        query.setParameter("week", week);
        return query.getResultList();
    }

    @Override
    public List<Reservations> getCurrentDateReservations(){
        Query query = getEntityManager().createNamedQuery("getAllWeekReservations", Reservations.class);
        Calendar c = Calendar.getInstance();
        query.setParameter("year", c.get(Calendar.YEAR));
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

    @Override
    public void remove(Reservations entity) {
        getDependencies();

        Reservations reservation = this.find(entity.getId());
        ReservationTypes reservationType = reservation.getReservationType();
        List<Reservations> reservationsCollection = reservationType.getReservationsCollection();
        reservationsCollection.remove(reservation);
        reservationType.setReservationsCollection(reservationsCollection);
        reservationTypesFacadeRemote.merge(reservationType);

        RoomSchedule roomSchedule = reservation.getRoomSchedule();
        reservationsCollection = roomSchedule.getReservationsCollection();
        reservationsCollection.remove(reservation);
        roomSchedule.setReservationsCollection(reservationsCollection);
        roomScheduleFacadeRemote.merge(roomSchedule);

        super.remove(reservation);
    }

    @Override
    protected void getDependencies(){
        try {
            roomScheduleFacadeRemote = new RoomScheduleFacade();
            reservationTypesFacadeRemote = new ReservationTypesFacade();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        Integer priviligeLevel = this.getPriviligeContext().getPriviligeLevel();
        roomScheduleFacadeRemote.setPriviligeLevel(priviligeLevel);
        reservationTypesFacadeRemote.setPriviligeLevel(priviligeLevel);
    }
}
