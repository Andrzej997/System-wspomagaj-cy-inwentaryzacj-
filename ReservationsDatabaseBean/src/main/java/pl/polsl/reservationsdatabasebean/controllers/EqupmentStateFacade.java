package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.polsl.reservationsdatabasebeanremote.database.EqupmentState;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.EqupmentStateFacadeRemote;

/**
 *
 * @author matis
 */
@Stateful
public class EqupmentStateFacade extends AbstractFacade<EqupmentState> implements EqupmentStateFacadeRemote {

    @PersistenceContext(unitName = "ReservationAdminPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EqupmentStateFacade() {
        super(EqupmentState.class);
    }
    
}
