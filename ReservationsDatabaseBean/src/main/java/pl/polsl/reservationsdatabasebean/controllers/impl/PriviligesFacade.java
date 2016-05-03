package pl.polsl.reservationsdatabasebean.controllers.impl;

import pl.polsl.reservationsdatabasebean.entities.Priviliges;
import pl.polsl.reservationsdatabasebean.controllers.PriviligesFacadeRemote;

import javax.ejb.Stateful;
import javax.naming.NamingException;

/**
 * @author matis
 */
@Stateful(mappedName = "PriviligesFacade")
public class PriviligesFacade extends AbstractFacade<Priviliges> implements PriviligesFacadeRemote {

    private static final long serialVersionUID = -2451267310651460812L;

    public PriviligesFacade() throws NamingException {
        super(Priviliges.class);
    }

}
