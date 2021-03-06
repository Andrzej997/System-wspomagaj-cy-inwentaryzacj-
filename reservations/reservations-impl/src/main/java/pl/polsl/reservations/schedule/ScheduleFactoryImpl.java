package pl.polsl.reservations.schedule;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import pl.polsl.reservations.dto.ReservationDTO;
import pl.polsl.reservations.ejb.dao.ReservationsDao;
import pl.polsl.reservations.ejb.dao.RoomDao;
import pl.polsl.reservations.ejb.dao.RoomScheduleDao;
import pl.polsl.reservations.logger.LoggerImpl;

/**
 * Created by Krzysztof Stręk on 2016-05-12.
 * @version 1.0
 */
@Stateful
@Interceptors({LoggerImpl.class})
public class ScheduleFactoryImpl implements ScheduleFactory {

    @EJB
    ReservationsDao reservationsDAO;

    @EJB
    RoomScheduleDao roomScheduleDAO;

    @EJB
    RoomDao roomDAO;

    public ScheduleFactoryImpl() {
    }

    @Override
    public List<ReservationDTO> createSchedule(ScheduleStrategy strategy, int roomId, int year, boolean semester) {
        return strategy.createSchedule(roomId, year, semester, reservationsDAO, roomScheduleDAO, roomDAO);
    }

    @Override
    public void setUserContext(String certificate) {
        roomDAO.setUserContext(certificate);
        reservationsDAO.setUserContext(certificate);
        roomScheduleDAO.setUserContext(certificate);
    }
}
