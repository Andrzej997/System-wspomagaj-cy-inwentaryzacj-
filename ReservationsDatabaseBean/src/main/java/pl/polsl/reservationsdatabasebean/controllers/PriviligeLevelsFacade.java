package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.PriviligeLevels;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.PriviligeLevelsFacadeRemote;

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
