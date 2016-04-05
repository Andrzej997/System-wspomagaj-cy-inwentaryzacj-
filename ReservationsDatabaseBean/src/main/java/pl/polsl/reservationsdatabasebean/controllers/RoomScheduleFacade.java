package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomScheduleFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.RoomSchedule;

/**
 *
 * @author matis
 */
@Stateful
public class RoomScheduleFacade extends AbstractFacade<RoomSchedule> implements RoomScheduleFacadeRemote {

    @PersistenceContext(unitName = "ReservationAdminPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RoomScheduleFacade() {
        super(RoomSchedule.class);
    }
    
}
