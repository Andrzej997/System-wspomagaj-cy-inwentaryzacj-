package pl.polsl.reservationsdatabasebean.controllers.impl;

import pl.polsl.reservationsdatabasebean.entities.Institutes;
import pl.polsl.reservationsdatabasebean.controllers.InstitutesFacadeRemote;

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
