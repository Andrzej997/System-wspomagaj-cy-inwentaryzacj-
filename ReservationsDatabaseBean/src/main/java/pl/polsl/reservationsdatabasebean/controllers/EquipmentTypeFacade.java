package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import javax.naming.NamingException;
import pl.polsl.reservationsdatabasebeanremote.database.EquipmentType;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.EquipmentTypeFacadeRemote;

/**
 * @author matis
 */
//@Interceptors({LoggerImpl.class})
@Stateful(mappedName = "EquipmentTypeFacade")
public class EquipmentTypeFacade extends AbstractFacade<EquipmentType> implements EquipmentTypeFacadeRemote {

    private static final long serialVersionUID = -3795169270292695011L;

    public EquipmentTypeFacade() throws NamingException {
        super(EquipmentType.class);
    }

}
