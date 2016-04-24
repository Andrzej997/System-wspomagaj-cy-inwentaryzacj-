package pl.polsl.reservationsdatabasebean.context;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author matis
 */
@Stateful
public class TesterPrivilige implements IPrivilige{

    @PersistenceContext(unitName = "ReservationTestsPU")
    EntityManager entityManager;
    
    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
    
}
