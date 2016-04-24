package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import pl.polsl.reservationsdatabasebeanremote.database.Priviliges;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.PriviligesFacadeRemote;

/**
 *
 * @author matis
 */
@Stateful
public class PriviligesFacade extends AbstractFacade<Priviliges> implements PriviligesFacadeRemote {

    public PriviligesFacade() {
        super(Priviliges.class);
    }
    
}
