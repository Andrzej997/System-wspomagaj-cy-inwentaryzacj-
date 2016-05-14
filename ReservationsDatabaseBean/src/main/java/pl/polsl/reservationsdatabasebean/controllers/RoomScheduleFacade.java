package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Reservations;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.RoomSchedule;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.ReservationsFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomScheduleFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.interceptors.TransactionalInterceptor;
import pl.polsl.reservationsdatabasebeanremote.database.logger.LoggerImpl;

import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class, TransactionalInterceptor.class})
@Stateful
@TransactionManagement(value = TransactionManagementType.BEAN)
public class RoomScheduleFacade extends AbstractFacade<RoomSchedule> implements RoomScheduleFacadeRemote {

    private static final long serialVersionUID = -8439468008559137683L;

    private ReservationsFacadeRemote reservationsFacadeRemote;

    private RoomFacadeRemote roomFacadeRemote;

    public RoomScheduleFacade() throws NamingException {
        super(RoomSchedule.class);
    }

    @Override
    public List<RoomSchedule> getAllSchedulesByYearAndSemester(int year, boolean semester){
        Query query = this.getEntityManager().createNamedQuery("getAllSchedulesByYearAndSemester", RoomSchedule.class);
        Date date = new java.sql.Date(year-1900, 0, 1);
        query.setParameter("year", date);
        query.setParameter("semester", semester);
        return query.getResultList();
    }

    @Override
    public List<RoomSchedule> getAllSchedulesAtSession(int year, boolean semester){
        Query query = this.getEntityManager().createNamedQuery("getAllSchedulesAtSession", RoomSchedule.class);
        Date date = new java.sql.Date(year-1900, 0, 1);
        query.setParameter("year", date);
        query.setParameter("semester", semester);
        return query.getResultList();
    }

    @Override
    public RoomSchedule getCurrentDateSchedule(int year, int week,boolean semester, Room room){
        Query query = this.getEntityManager().createNamedQuery("getCurrentDateSchedule", RoomSchedule.class);
        Date date = new java.sql.Date(year-1900, 0, 1);
        query.setParameter("year", date);
        query.setParameter("week", week);
        query.setParameter("room", room);
        query.setParameter("semester", semester);
        return (RoomSchedule) query.getSingleResult();
    }

    @Override
    public RoomSchedule getCurrentScheduleForRoom(int roomNumber){
        getDependencies();
        Query query = this.getEntityManager().createNamedQuery("getCurrentScheduleForRoom", RoomSchedule.class);
        Date date = new Date();
        Boolean semester;
        semester = date.getMonth() > 1 && date.getMonth() < 9;
        RoomSchedule currentDateSchedule = getCurrentDateSchedule(date.getYear(), 0,semester ,roomFacadeRemote.getRoomByNumber(roomNumber));
        query.setParameter("roomNumber", roomNumber);
        query.setParameter("RoomSchedule", currentDateSchedule);
        return (RoomSchedule) query.getSingleResult();
    }

    @Override
    public List<Reservations> getReservationsCollectionById(Number id){
        RoomSchedule roomSchedule = this.find(id);
        return roomSchedule.getReservationsCollection();
    }

    @Override
    public void remove(RoomSchedule entity) {
        getDependencies();

        RoomSchedule roomSchedule = this.find(entity.getId());
        List<Reservations> reservationsCollection = roomSchedule.getReservationsCollection();
        for(Reservations reservation : reservationsCollection){
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
    protected void getDependencies(){
        try {
            reservationsFacadeRemote = new ReservationsFacade();
            roomFacadeRemote = new RoomFacade();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        Integer priviligeLevel = this.getPriviligeContext().getPriviligeLevel();
        reservationsFacadeRemote.setPriviligeLevel(priviligeLevel);
        roomFacadeRemote.setPriviligeLevel(priviligeLevel);
    }
}
