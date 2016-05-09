package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebean.logger.LoggerImpl;
import pl.polsl.reservationsdatabasebeanremote.database.EqupmentState;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.EquipmentStateFacadeRemote;

import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;

/**
 * @author matis
 */
//@Interceptors({LoggerImpl.class})
@Stateful
public class EquipmentStateFacade extends AbstractFacade<EqupmentState> implements EquipmentStateFacadeRemote {

    private static final long serialVersionUID = -2753370797145954647L;

    public EquipmentStateFacade() throws NamingException {
        super(EqupmentState.class);
    }

}
