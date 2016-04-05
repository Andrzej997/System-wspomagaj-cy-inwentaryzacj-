package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.polsl.reservationsdatabasebeanremote.database.Priviliges;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.PriviligesFacadeRemote;

/**
 *
 * @author matis
 */
@Stateful
public class PriviligesFacade extends AbstractFacade<Priviliges> implements PriviligesFacadeRemote {

    @PersistenceContext(unitName = "ReservationAdminPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PriviligesFacade() {
        super(Priviliges.class);
    }
    
}
