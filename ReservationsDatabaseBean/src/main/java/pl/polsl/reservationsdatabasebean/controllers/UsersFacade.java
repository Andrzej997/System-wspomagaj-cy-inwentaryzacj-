package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.UsersFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.Users;

/**
 *
 * @author matis
 */
@Stateful
public class UsersFacade extends AbstractFacade<Users> implements UsersFacadeRemote {

    @PersistenceContext(unitName = "ReservationAdminPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }
    
}
