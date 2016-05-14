package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebean.logger.LoggerImpl;
import pl.polsl.reservationsdatabasebeanremote.database.ReservationTypes;
import pl.polsl.reservationsdatabasebeanremote.database.Reservations;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.ReservationTypesFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.ReservationsFacadeRemote;

import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import java.util.List;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class})
@Stateful
@TransactionManagement(value = TransactionManagementType.BEAN)
public class ReservationTypesFacade extends AbstractFacade<ReservationTypes> implements ReservationTypesFacadeRemote {

    private static final long serialVersionUID = -3961133009017351835L;
    private ReservationsFacadeRemote reservationsFacadeRemote;

    public ReservationTypesFacade() throws NamingException{
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

    protected void getDependencies(){
        try {
            reservationsFacadeRemote = new ReservationsFacade();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        Integer priviligeLevel = this.getPriviligeContext().getPriviligeLevel();
        reservationsFacadeRemote.setPriviligeLevel(priviligeLevel);
    }
}
