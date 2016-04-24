package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import pl.polsl.reservationsdatabasebeanremote.database.EqupmentState;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.EqupmentStateFacadeRemote;

/**
 *
 * @author matis
 */
@Stateful
public class EqupmentStateFacade extends AbstractFacade<EqupmentState> implements EqupmentStateFacadeRemote {

    public EqupmentStateFacade() {
        super(EqupmentState.class);
    }
    
}
