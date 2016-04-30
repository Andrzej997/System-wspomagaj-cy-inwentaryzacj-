package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Institutes;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.InstitutesFacadeRemote;

import javax.ejb.Stateful;
import javax.naming.NamingException;

/**
 * @author matis
 */
@Stateful(mappedName = "InstitutesFacade")
public class InstitutesFacade extends AbstractFacade<Institutes> implements InstitutesFacadeRemote {

    private static final long serialVersionUID = 6300433953924621009L;

    public InstitutesFacade() throws NamingException {
        super(Institutes.class);
    }

}
