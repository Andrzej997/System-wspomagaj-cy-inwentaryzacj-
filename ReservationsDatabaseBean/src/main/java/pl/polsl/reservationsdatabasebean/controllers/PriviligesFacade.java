package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import pl.polsl.reservationsdatabasebean.logger.LoggerImpl;
import pl.polsl.reservationsdatabasebeanremote.database.PriviligeLevels;
import pl.polsl.reservationsdatabasebeanremote.database.Priviliges;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.PriviligesFacadeRemote;

import java.util.List;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class})
@Stateful
public class PriviligesFacade extends AbstractFacade<Priviliges> implements PriviligesFacadeRemote {

    private static final long serialVersionUID = -2451267310651460812L;

    public PriviligesFacade() throws NamingException {
        super(Priviliges.class);
    }

    @Override
    public List<PriviligeLevels> getPriviligeLevelsCollectionById(Number id){
        Priviliges priviliges = this.find(id);
        return priviliges.getPriviligeLevelsCollection();
    }
}
