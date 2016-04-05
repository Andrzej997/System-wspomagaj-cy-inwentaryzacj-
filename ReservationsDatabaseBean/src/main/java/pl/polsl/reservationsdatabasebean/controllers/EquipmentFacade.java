package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.EquipmentFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.Equipment;

/**
 *
 * @author matis
 */
@Stateful
public class EquipmentFacade extends AbstractFacade<Equipment> implements EquipmentFacadeRemote {

    @PersistenceContext(unitName = "ReservationAdminPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EquipmentFacade() {
        super(Equipment.class);
    }
    
}
