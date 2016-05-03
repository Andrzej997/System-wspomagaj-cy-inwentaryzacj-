package pl.polsl.reservationsdatabasebean.controllers.impl;

import pl.polsl.reservationsdatabasebean.entities.Departaments;
import pl.polsl.reservationsdatabasebean.controllers.DepartamentsFacadeRemote;

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
