package pl.polsl.reservationsdatabasebean.controllers.impl;

import pl.polsl.reservationsdatabasebean.entities.RoomTypes;
import pl.polsl.reservationsdatabasebean.controllers.RoomTypesFacadeRemote;

import javax.ejb.Stateful;
import javax.naming.NamingException;

/**
 * @author matis
 */
@Stateful(mappedName = "RoomTypesFacade")
public class RoomTypesFacade extends AbstractFacade<RoomTypes> implements RoomTypesFacadeRemote {

    private static final long serialVersionUID = 3614381092644979715L;

    public RoomTypesFacade() throws NamingException {
        super(RoomTypes.class);
    }

}
