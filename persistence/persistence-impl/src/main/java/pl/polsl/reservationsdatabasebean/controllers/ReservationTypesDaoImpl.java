package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.PrivilegeLevelEnum;
import pl.polsl.reservationsdatabasebeanremote.database.ReservationTypes;
import pl.polsl.reservationsdatabasebeanremote.database.Reservations;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.ReservationTypesDao;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.ReservationsDao;
import pl.polsl.reservationsdatabasebeanremote.database.interceptors.TransactionalInterceptor;
import pl.polsl.reservationsdatabasebeanremote.database.logger.LoggerImpl;

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
        PrivilegeLevelEnum privilegeLevel = this.getPriviligeLevel();
        reservationsFacadeRemote.setPriviligeLevel(privilegeLevel);
    }
}
