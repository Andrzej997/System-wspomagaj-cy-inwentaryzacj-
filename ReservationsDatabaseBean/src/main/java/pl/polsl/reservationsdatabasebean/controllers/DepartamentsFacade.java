package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Departaments;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.DepartamentsFacadeRemote;

import javax.ejb.Stateful;
import javax.naming.NamingException;

/**
 * @author matis
 */
@Stateful(mappedName = "DepartamentsFacade")
public class DepartamentsFacade extends AbstractFacade<Departaments> implements DepartamentsFacadeRemote {

    private static final long serialVersionUID = 1982444506455129579L;

    public DepartamentsFacade() throws NamingException {
        super(Departaments.class);
    }

}
