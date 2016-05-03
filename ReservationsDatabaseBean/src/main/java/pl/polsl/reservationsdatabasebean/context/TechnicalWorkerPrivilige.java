package pl.polsl.reservationsdatabasebean.context;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author matis
 */
public class TechnicalWorkerPrivilige implements IPrivilige {

    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("ReservationTechnicalWorkerPU");

    @Override
    public EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }

}
