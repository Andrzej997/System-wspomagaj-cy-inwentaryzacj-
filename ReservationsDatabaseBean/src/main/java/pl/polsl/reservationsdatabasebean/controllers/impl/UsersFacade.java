package pl.polsl.reservationsdatabasebean.controllers.impl;

import pl.polsl.reservationsdatabasebean.entities.Users;
import pl.polsl.reservationsdatabasebean.controllers.UsersFacadeRemote;

import javax.ejb.Stateful;
import javax.jws.WebMethod;
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

    @WebMethod
    public String helloWorld() {
        return "Hello WOrld!";
    }

}
