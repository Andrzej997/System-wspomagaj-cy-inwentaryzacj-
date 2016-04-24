package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import pl.polsl.reservationsdatabasebeanremote.database.Equipment;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.EquipmentFacadeRemote;

/**
 *
 * @author matis
 */
@Stateful
public class EquipmentFacade extends AbstractFacade<Equipment> implements EquipmentFacadeRemote {

    public EquipmentFacade() {
        super(Equipment.class);
    }
    
}
