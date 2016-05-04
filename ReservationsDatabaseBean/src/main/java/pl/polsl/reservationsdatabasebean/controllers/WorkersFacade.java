package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Workers;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.WorkersFacadeRemote;

import javax.ejb.Stateful;
import javax.naming.NamingException;

/**
 * @author matis
 */
@Stateful(mappedName = "WorkersFacade")
public class WorkersFacade extends AbstractFacade<Workers> implements WorkersFacadeRemote {

    private static final long serialVersionUID = -509559572309358716L;

    public WorkersFacade() throws NamingException {
        super(Workers.class);
    }

}
