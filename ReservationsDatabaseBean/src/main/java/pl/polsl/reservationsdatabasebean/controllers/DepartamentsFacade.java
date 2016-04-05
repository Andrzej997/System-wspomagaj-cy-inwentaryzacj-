package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.polsl.reservationsdatabasebeanremote.database.Departaments;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.DepartamentsFacadeRemote;

/**
 *
 * @author matis
 */
@Stateful
public class DepartamentsFacade extends AbstractFacade<Departaments> implements DepartamentsFacadeRemote {

    @PersistenceContext(unitName = "ReservationAdminPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DepartamentsFacade() {
        super(Departaments.class);
    }
    
}
