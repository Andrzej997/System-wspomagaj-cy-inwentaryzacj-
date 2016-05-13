package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import pl.polsl.reservationsdatabasebean.logger.LoggerImpl;
import pl.polsl.reservationsdatabasebeanremote.database.PriviligeLevels;
import pl.polsl.reservationsdatabasebeanremote.database.Priviliges;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.PriviligeLevelsFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.PriviligesFacadeRemote;

import java.util.List;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class})
@Stateful
public class PriviligesFacade extends AbstractFacade<Priviliges> implements PriviligesFacadeRemote {

    private static final long serialVersionUID = -2451267310651460812L;

    private PriviligeLevelsFacadeRemote priviligeLevelsFacadeRemote;

    public PriviligesFacade() throws NamingException {
        super(Priviliges.class);
    }

    @Override
    public List<PriviligeLevels> getPriviligeLevelsCollectionById(Number id){
        Priviliges priviliges = this.find(id);
        return priviliges.getPriviligeLevelsCollection();
    }

    @Override
    public void remove(Object id){
        getDependencies();

        Priviliges privilige = this.find(id);
        List<PriviligeLevels> priviligeLevelsCollection = privilige.getPriviligeLevelsCollection();
        for(PriviligeLevels priviligeLevels : priviligeLevelsCollection){
            List<Priviliges> priviligesCollection = priviligeLevels.getPriviligesCollection();
            priviligesCollection.remove(privilige);
            priviligeLevels.setPriviligesCollection(priviligesCollection);
            priviligeLevelsFacadeRemote.merge(priviligeLevels);
        }

        super.remove(privilige.getPriviligeId());
    }

    protected void getDependencies(){
        try {
            priviligeLevelsFacadeRemote = new PriviligeLevelsFacade();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        Integer priviligeLevel = this.getPriviligeContext().getPriviligeLevel();
        priviligeLevelsFacadeRemote.setPriviligeLevel(priviligeLevel);
    }
}
