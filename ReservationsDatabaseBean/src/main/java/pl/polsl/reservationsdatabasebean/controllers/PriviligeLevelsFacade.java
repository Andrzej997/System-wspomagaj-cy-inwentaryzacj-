package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import pl.polsl.reservationsdatabasebean.logger.LoggerImpl;
import pl.polsl.reservationsdatabasebeanremote.database.PriviligeLevels;
import pl.polsl.reservationsdatabasebeanremote.database.Priviliges;
import pl.polsl.reservationsdatabasebeanremote.database.Users;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.PriviligeLevelsFacadeRemote;

import java.util.List;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class})
@Stateful
public class PriviligeLevelsFacade extends AbstractFacade<PriviligeLevels> implements PriviligeLevelsFacadeRemote {

    private static final long serialVersionUID = 940693942951656679L;

    public PriviligeLevelsFacade() throws NamingException {
        super(PriviligeLevels.class);
    }

    @Override
    public List<Priviliges> getPriviligesCollectionById(Number id){
        PriviligeLevels priviligeLevels = this.find(id);
        return priviligeLevels.getPriviligesCollection();
    }

    @Override
    public List<Users> getUsersCollectionById(Number id){
        PriviligeLevels priviligeLevels = this.find(id);
        return priviligeLevels.getUsersCollection();
    }
}
