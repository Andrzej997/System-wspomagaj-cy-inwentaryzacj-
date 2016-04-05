package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.ReservationTypesFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.ReservationTypes;

/**
 *
 * @author matis
 */
@Stateful
public class ReservationTypesFacade extends AbstractFacade<ReservationTypes> implements ReservationTypesFacadeRemote {

    @PersistenceContext(unitName = "ReservationAdminPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReservationTypesFacade() {
        super(ReservationTypes.class);
    }
    
}
