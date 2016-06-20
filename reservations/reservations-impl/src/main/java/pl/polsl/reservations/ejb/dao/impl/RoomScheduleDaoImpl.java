package pl.polsl.reservations.ejb.dao.impl;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import pl.polsl.reservations.ejb.dao.ReservationsDao;
import pl.polsl.reservations.ejb.dao.RoomDao;
import pl.polsl.reservations.ejb.dao.RoomScheduleDao;
import pl.polsl.reservations.entities.Reservations;
import pl.polsl.reservations.entities.Room;
import pl.polsl.reservations.entities.RoomSchedule;
import pl.polsl.reservations.interceptors.TransactionalInterceptor;
import pl.polsl.reservations.logger.LoggerImpl;

/**
 * @author Mateusz Sojka
 * @version 1.0
 *
 * RoomSchedule data access object implementation
 */
@Interceptors({LoggerImpl.class, TransactionalInterceptor.class})
@Stateful
@TransactionManagement(value = TransactionManagementType.BEAN)
public class RoomScheduleDaoImpl extends AbstractDaoImpl<RoomSchedule> implements RoomScheduleDao {

    private static final long serialVersionUID = -8439468008559137683L;

    private ReservationsDao reservationsFacadeRemote;

    private RoomDao roomFacadeRemote;

    public RoomScheduleDaoImpl() throws NamingException {
        super(RoomSchedule.class);
    }

    /**
     * Method to get all schedules by year and semester
     *
     * @param year int schedule year
     * @param semester boolean schedule semester (true means summer)
     * @return list of room schedules
     */
    @Override
    public List<RoomSchedule> getAllSchedulesByYearAndSemester(int year, boolean semester) {
        Query query = this.getEntityManager().createNamedQuery("getAllSchedulesByYearAndSemester", RoomSchedule.class);
        query.setParameter("year", year);
        query.setParameter("semester", semester);
        return query.getResultList();
    }

    /**
     * Method to get all schedules by year and semester at session
     *
     * @param year int schedule year
     * @param semester boolean schedule semester (true means summer)
     * @return list of room schedules
     */
    @Override
    public List<RoomSchedule> getAllSchedulesAtSession(int year, boolean semester) {
        Query query = this.getEntityManager().createNamedQuery("getAllSchedulesAtSession", RoomSchedule.class);
        query.setParameter("year", year);
        query.setParameter("semester", semester);
        return query.getResultList();
    }

    /**
     * Method to get schedule
     *
     * @param year int schedule year
     * @param week int schedule week
     * @param semester boolean schedule semester (true means summer)
     * @param room Room entity
     * @return RoomSchedule entity
     */
    @Override
    public RoomSchedule getCurrentDateSchedule(int year, int week, boolean semester, Room room) {
        Query query = this.getEntityManager().createNamedQuery("getCurrentDateSchedule", RoomSchedule.class);
        query.setParameter("year", year);
        query.setParameter("week", week);
        query.setParameter("room", room);
        query.setParameter("semester", semester);
        try {
            return (RoomSchedule) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    /**
     * Method to get current date schedule
     *
     * @param roomNumber room number
     * @return RoomSchedule entity
     */
    @Override
    public RoomSchedule getCurrentScheduleForRoom(int roomNumber) {
        getDependencies();
        Query query = this.getEntityManager().createNamedQuery("getCurrentScheduleForRoom", RoomSchedule.class);
        Date date = new Date();
        Boolean semester;
        semester = date.getMonth() > 1 && date.getMonth() < 9;
        Room room = roomFacadeRemote.getRoomByNumber(roomNumber);
        if (room != null) {
            RoomSchedule currentDateSchedule = getCurrentDateSchedule(date.getYear(), 0, semester, room);
            query.setParameter("roomNumber", roomNumber);
            query.setParameter("RoomSchedule", currentDateSchedule);
            try {
                return (RoomSchedule) query.getSingleResult();
            } catch (NoResultException ex) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Method to get reservations collection by schedule id
     *
     * @param id schedule id
     * @return list of reservations
     */
    @Override
    public List<Reservations> getReservationsCollectionById(Number id) {
        RoomSchedule roomSchedule = this.find(id);
        return roomSchedule.getReservationsCollection();
    }

    /**
     * Method used to remove entity from database
     *
     * @param entity
     */
    @Override
    public void remove(RoomSchedule entity) {
        getDependencies();

        RoomSchedule roomSchedule = this.find(entity.getId());
        List<Reservations> reservationsCollection = roomSchedule.getReservationsCollection();
        for (Reservations reservation : reservationsCollection) {
            reservationsFacadeRemote.remove(reservation);
        }

        Room room = roomSchedule.getRoom();
        List<RoomSchedule> roomScheduleCollection = room.getRoomScheduleCollection();
        roomScheduleCollection.remove(roomSchedule);
        room.setRoomScheduleCollection(roomScheduleCollection);
        roomFacadeRemote.merge(room);

        super.remove(roomSchedule);
    }

    @Override
    protected void getDependencies() {
        try {
            reservationsFacadeRemote = new ReservationsDaoImpl();
            roomFacadeRemote = new RoomDaoImpl();
            reservationsFacadeRemote.setUserContext(userContext);
            roomFacadeRemote.setUserContext(userContext);
        } catch (NamingException e) {
        }
    }
}
