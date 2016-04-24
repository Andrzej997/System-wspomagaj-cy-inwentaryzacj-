package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import pl.polsl.reservationsdatabasebeanremote.database.EquipmentType;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.EquipmentTypeFacadeRemote;

/**
 *
 * @author matis
 */
@Stateful
public class EquipmentTypeFacade extends AbstractFacade<EquipmentType> implements EquipmentTypeFacadeRemote {

    public EquipmentTypeFacade() {
        super(EquipmentType.class);
    }

}
