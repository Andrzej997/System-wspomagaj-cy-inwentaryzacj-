package pl.polsl.reservationsdatabasebean.controllers.impl;

import pl.polsl.reservationsdatabasebean.entities.PriviligeLevels;
import pl.polsl.reservationsdatabasebean.controllers.PriviligeLevelsFacadeRemote;

import javax.ejb.Stateful;
import javax.naming.NamingException;

/**
 * @author matis
 */
@Stateful(mappedName = "PriviligeLevelsFacade")
public class PriviligeLevelsFacade extends AbstractFacade<PriviligeLevels> implements PriviligeLevelsFacadeRemote {

    private static final long serialVersionUID = 940693942951656679L;

    public PriviligeLevelsFacade() throws NamingException {
        super(PriviligeLevels.class);
    }

}
