package pl.polsl.reservations.ejb.dao.impl;

import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import pl.polsl.reservations.ejb.dao.PriviligeLevelsDao;
import pl.polsl.reservations.ejb.dao.PriviligesDao;
import pl.polsl.reservations.entities.PriviligeLevels;
import pl.polsl.reservations.entities.Priviliges;
import pl.polsl.reservations.interceptors.TransactionalInterceptor;
import pl.polsl.reservations.logger.LoggerImpl;

/**
 * @author Mateusz Sojka
 * @version 1.0
 *
 * Privilege data access object implementation
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

    /**
     * Method to get privilege levels which has given privilege
     *
     * @param id privilege id
     * @return list of privilege levels
     */
    @Override
    public List<PriviligeLevels> getPriviligeLevelsCollectionById(Number id) {
        Priviliges priviliges = this.find(id);
        return priviliges.getPriviligeLevelsCollection();
    }

    /**
     * Method used to remove entity from database
     *
     * @param entity entity to remove
     */
    @Override
    public void remove(Priviliges entity) {
        getDependencies();

        Priviliges privilige = this.find(entity.getId());
        List<PriviligeLevels> priviligeLevelsCollection = privilige.getPriviligeLevelsCollection();
        for (PriviligeLevels priviligeLevels : priviligeLevelsCollection) {
            List<Priviliges> priviligesCollection = priviligeLevels.getPriviligesCollection();
            priviligesCollection.remove(privilige);
            priviligeLevels.setPriviligesCollection(priviligesCollection);
            priviligeLevelsFacadeRemote.merge(priviligeLevels);
        }

        super.remove(privilige);
    }

    @Override
    protected void getDependencies() {
        try {
            priviligeLevelsFacadeRemote = new PriviligeLevelsDaoImpl();
            priviligeLevelsFacadeRemote.setUserContext(userContext);
        } catch (NamingException e) {
        }
    }
}
