package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.polsl.reservationsdatabasebeanremote.database.PriviligeLevels;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.PriviligeLevelsFacadeRemote;

/**
 *
 * @author matis
 */
@Stateful
public class PriviligeLevelsFacade extends AbstractFacade<PriviligeLevels> implements PriviligeLevelsFacadeRemote {

    @PersistenceContext(unitName = "ReservationAdminPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PriviligeLevelsFacade() {
        super(PriviligeLevels.class);
    }
    
}
