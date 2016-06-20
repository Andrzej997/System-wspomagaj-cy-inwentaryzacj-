package pl.polsl.reservations.ejb.dao.impl;

import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.persistence.Query;
import pl.polsl.reservations.ejb.dao.ReservationTypesDao;
import pl.polsl.reservations.ejb.dao.ReservationsDao;
import pl.polsl.reservations.ejb.dao.RoomScheduleDao;
import pl.polsl.reservations.entities.ReservationTypes;
import pl.polsl.reservations.entities.Reservations;
import pl.polsl.reservations.entities.RoomSchedule;
import pl.polsl.reservations.interceptors.TransactionalInterceptor;
import pl.polsl.reservations.logger.LoggerImpl;

/**
 * @author Mateusz Sojka
 * @version 1.0
 *
 * Reservation data access object implementation
 */
@Interceptors({LoggerImpl.class, TransactionalInterceptor.class})
@Stateful
@TransactionManagement(value = TransactionManagementType.BEAN)
public class ReservationsDaoImpl extends AbstractDaoImpl<Reservations> implements ReservationsDao {

    private static final long serialVersionUID = 8947630232538216657L;
    private RoomScheduleDao roomScheduleFacadeRemote;
    private ReservationTypesDao reservationTypesFacadeRemote;

    public ReservationsDaoImpl() throws NamingException {
        super(Reservations.class);
    }

    /**
     * Method to get all reservations from room schedule
     *
     * @param roomSchedule RoomSchedule entity
     * @return list of reservations
     */
    @Override
    public List<Reservations> getAllReservationsByRoomSchedule(RoomSchedule roomSchedule) {
        Query query = getEntityManager().createNamedQuery("getAllReservationsByRoomSchedule", Reservations.class);
        query.setParameter("roomSchedule", roomSchedule);
        return query.getResultList();
    }

    /**
     * Method to get all week reservations
     *
     * @param week int reservation week
     * @param year int reservation year
     * @return list of reservations
     */
    @Override
    public List<Reservations> getAllWeekReservations(int week, int year) {
        Query query = getEntityManager().createNamedQuery("getAllWeekReservations", Reservations.class);
        query.setParameter("year", year);
        query.setParameter("week", week);
        return query.getResultList();
    }

    /**
     * Method to get current date reservations
     *
     * @return List of reservations
     */
    @Override
    public List<Reservations> getCurrentDateReservations() {
        Query query = getEntityManager().createNamedQuery("getAllWeekReservations", Reservations.class);
        Calendar c = Calendar.getInstance();
        query.setParameter("year", c.get(Calendar.YEAR));
        query.setParameter("week", c.get(Calendar.WEEK_OF_YEAR));
        return query.getResultList();
    }

    /**
     * Method to get all reservations by type
     *
     * @param typeId reservation type id
     * @return list of reservations
     */
    @Override
    public List<Reservations> getAllReservationsByType(Long typeId) {
        Query query = getEntityManager().createNamedQuery("getAllReservationsByType", Reservations.class);
        query.setParameter("typeId", typeId);
        return query.getResultList();
    }

    /**
     * Method to get all reservations by user id
     *
     * @param userId user id
     * @return list of reservations
     */
    @Override
    public List<Reservations> getAllReservationsByUser(Long userId) {
        Query query = getEntityManager().createNamedQuery("getAllReservationsByUser", Reservations.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    /**
     * Method used to remove entity from database
     *
     * @param entity entity to remove
     */
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
    protected void getDependencies() {
        try {
            roomScheduleFacadeRemote = new RoomScheduleDaoImpl();
            reservationTypesFacadeRemote = new ReservationTypesDaoImpl();
            roomScheduleFacadeRemote.setUserContext(userContext);
            reservationTypesFacadeRemote.setUserContext(userContext);
        } catch (NamingException e) {
        }
    }
}
