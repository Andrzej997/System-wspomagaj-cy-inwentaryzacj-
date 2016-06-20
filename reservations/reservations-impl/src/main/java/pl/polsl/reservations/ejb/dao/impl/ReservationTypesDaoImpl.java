package pl.polsl.reservations.ejb.dao.impl;

import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import pl.polsl.reservations.ejb.dao.ReservationTypesDao;
import pl.polsl.reservations.ejb.dao.ReservationsDao;
import pl.polsl.reservations.entities.ReservationTypes;
import pl.polsl.reservations.entities.Reservations;
import pl.polsl.reservations.interceptors.TransactionalInterceptor;
import pl.polsl.reservations.logger.LoggerImpl;

/**
 * @author Mateusz Sojka
 * @version 1.0
 *
 * ReservationTypes data access object implementation
 */
@Interceptors({LoggerImpl.class, TransactionalInterceptor.class})
@Stateful
@TransactionManagement(value = TransactionManagementType.BEAN)
public class ReservationTypesDaoImpl extends AbstractDaoImpl<ReservationTypes> implements ReservationTypesDao {

    private static final long serialVersionUID = -3961133009017351835L;
    private ReservationsDao reservationsFacadeRemote;

    public ReservationTypesDaoImpl() throws NamingException {
        super(ReservationTypes.class);
    }

    /**
     * Get reservations which has given type
     *
     * @param id reservation type id
     * @return list of reservations
     */
    @Override
    public List<Reservations> getReservationsCollectionById(Number id) {
        ReservationTypes reservationTypes = this.find(id);
        return reservationTypes.getReservationsCollection();
    }

    /**
     * Method used to remove entity from database
     *
     * @param entity
     */
    @Override
    public void remove(ReservationTypes entity) {
        getDependencies();

        ReservationTypes reservationType = this.find(entity.getId());
        List<Reservations> reservationsCollection = reservationType.getReservationsCollection();
        for (Reservations reservation : reservationsCollection) {
            reservationsFacadeRemote.remove(reservation);
        }

        super.remove(reservationType);
    }

    @Override
    protected void getDependencies() {
        try {
            reservationsFacadeRemote = new ReservationsDaoImpl();
            reservationsFacadeRemote.setUserContext(userContext);
        } catch (NamingException e) {
        }
    }
}
