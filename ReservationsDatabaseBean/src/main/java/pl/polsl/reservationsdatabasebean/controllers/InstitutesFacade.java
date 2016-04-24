package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import pl.polsl.reservationsdatabasebeanremote.database.Institutes;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.InstitutesFacadeRemote;

/**
 *
 * @author matis
 */
@Stateful
public class InstitutesFacade extends AbstractFacade<Institutes> implements InstitutesFacadeRemote {
    
    public InstitutesFacade() {
        super(Institutes.class);
    }
    
}
