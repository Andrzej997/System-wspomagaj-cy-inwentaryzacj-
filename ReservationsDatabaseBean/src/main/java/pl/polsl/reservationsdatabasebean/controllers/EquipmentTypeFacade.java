package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebean.logger.LoggerImpl;
import pl.polsl.reservationsdatabasebeanremote.database.EquipmentType;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.EquipmentTypeFacadeRemote;

import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;

/**
 * @author matis
 */
//@Interceptors({LoggerImpl.class})
@Stateful
public class EquipmentTypeFacade extends AbstractFacade<EquipmentType> implements EquipmentTypeFacadeRemote {

    private static final long serialVersionUID = -3795169270292695011L;

    public EquipmentTypeFacade() throws NamingException {
        super(EquipmentType.class);
    }

}
