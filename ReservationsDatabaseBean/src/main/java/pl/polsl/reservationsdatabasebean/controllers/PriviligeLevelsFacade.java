package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import pl.polsl.reservationsdatabasebeanremote.database.PriviligeLevels;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.PriviligeLevelsFacadeRemote;

/**
 *
 * @author matis
 */
@Stateful
public class PriviligeLevelsFacade extends AbstractFacade<PriviligeLevels> implements PriviligeLevelsFacadeRemote {

    public PriviligeLevelsFacade() {
        super(PriviligeLevels.class);
    }
    
}
