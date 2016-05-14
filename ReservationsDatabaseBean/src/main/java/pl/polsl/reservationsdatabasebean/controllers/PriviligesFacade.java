package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.PriviligeLevels;
import pl.polsl.reservationsdatabasebeanremote.database.Priviliges;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.PriviligeLevelsFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.PriviligesFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.interceptors.TransactionalInterceptor;
import pl.polsl.reservationsdatabasebeanremote.database.logger.LoggerImpl;

import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import java.util.List;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class, TransactionalInterceptor.class})
@Stateful
@TransactionManagement(value = TransactionManagementType.BEAN)
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
    public void remove(Priviliges entity) {
        getDependencies();

        Priviliges privilige = this.find(entity.getPriviligeId());
        List<PriviligeLevels> priviligeLevelsCollection = privilige.getPriviligeLevelsCollection();
        for(PriviligeLevels priviligeLevels : priviligeLevelsCollection){
            List<Priviliges> priviligesCollection = priviligeLevels.getPriviligesCollection();
            priviligesCollection.remove(privilige);
            priviligeLevels.setPriviligesCollection(priviligesCollection);
            priviligeLevelsFacadeRemote.merge(priviligeLevels);
        }

        super.remove(privilige);
    }

    @Override
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
