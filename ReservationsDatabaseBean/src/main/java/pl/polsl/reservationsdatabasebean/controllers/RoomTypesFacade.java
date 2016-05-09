package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebean.logger.LoggerImpl;
import pl.polsl.reservationsdatabasebeanremote.database.RoomTypes;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomTypesFacadeRemote;

import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;

/**
 * @author matis
 */
//@Interceptors({LoggerImpl.class})
@Stateful
public class RoomTypesFacade extends AbstractFacade<RoomTypes> implements RoomTypesFacadeRemote {

    private static final long serialVersionUID = 3614381092644979715L;

    public RoomTypesFacade() throws NamingException {
        super(RoomTypes.class);
    }

}
