package pl.polsl.reservationsdatabasebean.context;

import pl.polsl.reservationsdatabasebeanremote.database.context.IPrivilige;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author matis
 */
public class TechnicalChiefPrivilige implements IPrivilige {

    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("ReservationTechnicalChiefPU");

    @Override
    public EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }

}
