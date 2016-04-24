package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import pl.polsl.reservationsdatabasebeanremote.database.Workers;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.WorkersFacadeRemote;

/**
 *
 * @author matis
 */
@Stateful
public class WorkersFacade extends AbstractFacade<Workers> implements WorkersFacadeRemote {

    public WorkersFacade() {
        super(Workers.class);
    }
    
}
