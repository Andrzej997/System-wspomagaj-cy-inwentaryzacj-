package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.ChiefsFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.Chiefs;

/**
 *
 * @author matis
 */
@Stateful
public class ChiefsFacade extends AbstractFacade<Chiefs> implements ChiefsFacadeRemote {

    @PersistenceContext(unitName = "ReservationAdminPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChiefsFacade() {
        super(Chiefs.class);
    }
    
}
