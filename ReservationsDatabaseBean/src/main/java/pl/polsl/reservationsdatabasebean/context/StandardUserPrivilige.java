package pl.polsl.reservationsdatabasebean.context;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author matis
 */
@Stateful
public class StandardUserPrivilige implements IPrivilige {

    @PersistenceContext(unitName = "ReservationAdminPU")
    EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

}
