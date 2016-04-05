package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.polsl.reservationsdatabasebeanremote.database.RoomTypes;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomTypesFacadeRemote;

/**
 *
 * @author matis
 */
@Stateful
public class RoomTypesFacade extends AbstractFacade<RoomTypes> implements RoomTypesFacadeRemote {

    @PersistenceContext(unitName = "ReservationAdminPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RoomTypesFacade() {
        super(RoomTypes.class);
    }
    
}
