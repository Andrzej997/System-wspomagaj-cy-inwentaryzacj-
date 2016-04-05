package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.polsl.reservationsdatabasebeanremote.database.Institutes;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.InstitutesFacadeRemote;

/**
 *
 * @author matis
 */
@Stateful
public class InstitutesFacade extends AbstractFacade<Institutes> implements InstitutesFacadeRemote {

    @PersistenceContext(unitName = "ReservationAdminPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InstitutesFacade() {
        super(Institutes.class);
    }
    
}
