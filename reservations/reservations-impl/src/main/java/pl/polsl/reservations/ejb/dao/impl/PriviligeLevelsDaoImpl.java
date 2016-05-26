package pl.polsl.reservations.ejb.dao.impl;

import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.persistence.Query;
import pl.polsl.reservations.ejb.dao.PriviligeLevelsDao;
import pl.polsl.reservations.ejb.dao.PriviligesDao;
import pl.polsl.reservations.ejb.dao.UsersDao;
import pl.polsl.reservations.entities.PriviligeLevels;
import pl.polsl.reservations.entities.Priviliges;
import pl.polsl.reservations.entities.Users;
import pl.polsl.reservations.interceptors.TransactionalInterceptor;
import pl.polsl.reservations.logger.LoggerImpl;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class, TransactionalInterceptor.class})
@Stateful
@TransactionManagement(value = TransactionManagementType.BEAN)
public class PriviligeLevelsDaoImpl extends AbstractDaoImpl<PriviligeLevels> implements PriviligeLevelsDao {

    private static final long serialVersionUID = 940693942951656679L;

    private PriviligesDao priviligesFacadeRemote;

    private UsersDao usersFacadeRemote;

    public PriviligeLevelsDaoImpl() throws NamingException {
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

    @Override
    public PriviligeLevels getPrivligeLevelsEntityByLevelValue(Long levelValue){
        Query query = getEntityManager().createNamedQuery("getPrivligeLevelsEntityByLevelValue", PriviligeLevels.class);
        query.setParameter("levelValue",levelValue);
        return (PriviligeLevels) query.getSingleResult();
    }

    @Override
    public void remove(PriviligeLevels entity) {
        getDependencies();

        PriviligeLevels priviligeLevel = this.find(entity.getPriviligeLevel());
        List<Priviliges> priviligesCollection = priviligeLevel.getPriviligesCollection();
        for(Priviliges priviliges : priviligesCollection){
            List<PriviligeLevels> priviligeLevelsCollection = priviliges.getPriviligeLevelsCollection();
            priviligeLevelsCollection.remove(priviligeLevel);
            priviliges.setPriviligeLevelsCollection(priviligeLevelsCollection);
            priviligesFacadeRemote.merge(priviliges);
        }

        List<Users> usersCollection = priviligeLevel.getUsersCollection();
        Long level = priviligeLevel.getPriviligeLevel();
        PriviligeLevels newPriviligeLevel = this.getPrivligeLevelsEntityByLevelValue(level - 1);
        for(Users user : usersCollection){
            if(newPriviligeLevel != null) {
                user.setPriviligeLevel(newPriviligeLevel);
                usersFacadeRemote.merge(user);
            } else {
                usersFacadeRemote.remove(user);
            }
        }
        super.remove(priviligeLevel);
    }

    @Override
    protected void getDependencies(){
        try {
            usersFacadeRemote = new UsersDaoImpl();
            priviligesFacadeRemote = new PriviligesDaoImpl();
            usersFacadeRemote.setUserContext(userContext);
            priviligesFacadeRemote.setUserContext(userContext);
        } catch (NamingException e) {
        }
    }
}
