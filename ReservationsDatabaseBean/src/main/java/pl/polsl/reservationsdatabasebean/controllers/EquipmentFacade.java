package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Equipment;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.EquipmentFacadeRemote;

import javax.ejb.Stateful;
import javax.naming.NamingException;

/**
 * @author matis
 */
@Stateful(mappedName = "EquipmentFacade")
public class EquipmentFacade extends AbstractFacade<Equipment> implements EquipmentFacadeRemote {

    private static final long serialVersionUID = 4691619751998264500L;

    public EquipmentFacade() throws NamingException {
        super(Equipment.class);
    }

}
