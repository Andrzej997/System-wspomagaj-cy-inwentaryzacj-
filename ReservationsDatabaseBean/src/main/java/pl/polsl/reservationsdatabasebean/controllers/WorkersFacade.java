package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.WorkersFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.Workers;

/**
 *
 * @author matis
 */
@Stateful
public class WorkersFacade extends AbstractFacade<Workers> implements WorkersFacadeRemote {

    @PersistenceContext(unitName = "ReservationAdminPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WorkersFacade() {
        super(Workers.class);
    }
    
}
