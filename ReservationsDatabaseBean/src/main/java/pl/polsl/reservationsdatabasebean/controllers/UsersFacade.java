package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import pl.polsl.reservationsdatabasebeanremote.database.Users;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.UsersFacadeRemote;

/**
 *
 * @author matis
 */
@Stateful
public class UsersFacade extends AbstractFacade<Users> implements UsersFacadeRemote {

    public UsersFacade() {
        super(Users.class);
    }
    
}
