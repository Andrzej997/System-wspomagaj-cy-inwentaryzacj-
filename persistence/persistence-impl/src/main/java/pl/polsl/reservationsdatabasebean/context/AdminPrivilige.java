package pl.polsl.reservationsdatabasebean.context;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pl.polsl.reservationsdatabasebeanremote.database.context.IPrivilige;

/**
 * @author matis
 */
public class AdminPrivilige implements IPrivilige {

    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("ReservationAdminPU");

    @Override
    public EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }

}