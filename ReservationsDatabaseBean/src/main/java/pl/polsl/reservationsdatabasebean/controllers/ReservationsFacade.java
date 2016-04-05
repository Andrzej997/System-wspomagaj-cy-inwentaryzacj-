package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.polsl.reservationsdatabasebeanremote.database.Reservations;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.ReservationsFacadeRemote;

/**
 *
 * @author matis
 */
@Stateful
public class ReservationsFacade extends AbstractFacade<Reservations> implements ReservationsFacadeRemote {

    @PersistenceContext(unitName = "ReservationAdminPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReservationsFacade() {
        super(Reservations.class);
    }
    
}
