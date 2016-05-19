package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.PrivilegeLevelEnum;
import pl.polsl.reservationsdatabasebeanremote.database.PriviligeLevels;
import pl.polsl.reservationsdatabasebeanremote.database.Priviliges;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.PriviligeLevelsDao;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.PriviligesDao;
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
public class PriviligesDaoImpl extends AbstractDaoImpl<Priviliges> implements PriviligesDao {

    private static final long serialVersionUID = -2451267310651460812L;

    private PriviligeLevelsDao priviligeLevelsFacadeRemote;

    public PriviligesDaoImpl() throws NamingException {
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
            priviligeLevelsFacadeRemote = new PriviligeLevelsDaoImpl();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        PrivilegeLevelEnum privilegeLevel = this.getPriviligeLevel();
        priviligeLevelsFacadeRemote.setPriviligeLevel(privilegeLevel);
    }
}
