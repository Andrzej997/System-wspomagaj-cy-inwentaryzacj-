package pl.polsl.reservations.ejb.dao.impl;

import pl.polsl.reservations.ejb.dao.ReservationTypesDao;
import pl.polsl.reservations.ejb.dao.ReservationsDao;
import pl.polsl.reservations.entities.ReservationTypes;
import pl.polsl.reservations.entities.Reservations;
import pl.polsl.reservations.interceptors.TransactionalInterceptor;
import pl.polsl.reservations.logger.LoggerImpl;

import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import java.util.List;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class, TransactionalInterceptor.class})
@Stateful
@TransactionManagement(value = TransactionManagementType.BEAN)
public class ReservationTypesDaoImpl extends AbstractDaoImpl<ReservationTypes> implements ReservationTypesDao {

    private static final long serialVersionUID = -3961133009017351835L;
    private ReservationsDao reservationsFacadeRemote;

    public ReservationTypesDaoImpl() throws NamingException{
        super(ReservationTypes.class);
    }

    @Override
    public List<Reservations> getReservationsCollectionById(Number id){
        ReservationTypes reservationTypes = this.find(id);
        return reservationTypes.getReservationsCollection();
    }

    @Override
    public void remove(ReservationTypes entity) {
        getDependencies();

        ReservationTypes reservationType = this.find(entity.getTypeId());
        List<Reservations> reservationsCollection = reservationType.getReservationsCollection();
        for(Reservations reservation : reservationsCollection){
            reservationsFacadeRemote.remove(reservation);
        }

        super.remove(reservationType);
    }

    @Override
    protected void getDependencies(){
        try {
            reservationsFacadeRemote = new ReservationsDaoImpl();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}