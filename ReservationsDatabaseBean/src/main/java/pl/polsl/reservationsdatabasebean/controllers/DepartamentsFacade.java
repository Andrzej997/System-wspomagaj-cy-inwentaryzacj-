package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import pl.polsl.reservationsdatabasebeanremote.database.Departaments;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.DepartamentsFacadeRemote;

/**
 *
 * @author matis
 */
@Stateful
public class DepartamentsFacade extends AbstractFacade<Departaments> implements DepartamentsFacadeRemote {

    public DepartamentsFacade() {
        super(Departaments.class);
    }
    
}
