package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import pl.polsl.reservationsdatabasebean.logger.LoggerImpl;
import pl.polsl.reservationsdatabasebeanremote.database.ReservationTypes;
import pl.polsl.reservationsdatabasebeanremote.database.Reservations;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.ReservationTypesFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.ReservationsFacadeRemote;

import java.util.List;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class})
@Stateful
public class ReservationTypesFacade extends AbstractFacade<ReservationTypes> implements ReservationTypesFacadeRemote {

    private ReservationsFacadeRemote reservationsFacadeRemote;

    private static final long serialVersionUID = -3961133009017351835L;

    public ReservationTypesFacade() throws NamingException{
        super(ReservationTypes.class);
    }

    @Override
    public List<Reservations> getReservationsCollectionById(Number id){
        ReservationTypes reservationTypes = this.find(id);
        return reservationTypes.getReservationsCollection();
    }

    @Override
    public void remove(Object id){
        getDependencies();

        ReservationTypes reservationType = this.find(id);
        List<Reservations> reservationsCollection = reservationType.getReservationsCollection();
        for(Reservations reservation : reservationsCollection){
            reservationsFacadeRemote.remove(reservation.getId());
        }

        super.remove(reservationType.getTypeId());
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
