package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Users;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.UsersFacadeRemote;

import javax.ejb.Stateful;
import javax.naming.NamingException;

/**
 * @author matis
 */
@Stateful(mappedName = "UsersFacade")
public class UsersFacade extends AbstractFacade<Users> implements UsersFacadeRemote {

    private static final long serialVersionUID = -8931746196880043035L;

    public UsersFacade() throws NamingException {
        super(Users.class);
    }

}
