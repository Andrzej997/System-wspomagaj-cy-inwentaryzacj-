package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.PriviligeLevels;
import pl.polsl.reservationsdatabasebeanremote.database.Priviliges;
import pl.polsl.reservationsdatabasebeanremote.database.Users;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.PriviligeLevelsFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.PriviligesFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.UsersFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.logger.LoggerImpl;

import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.persistence.Query;
import java.util.List;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class})
@Stateful
@TransactionManagement(value = TransactionManagementType.BEAN)
public class PriviligeLevelsFacade extends AbstractFacade<PriviligeLevels> implements PriviligeLevelsFacadeRemote {

    private static final long serialVersionUID = 940693942951656679L;

    private PriviligesFacadeRemote priviligesFacadeRemote;

    private UsersFacadeRemote usersFacadeRemote;

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

    protected void getDependencies(){
        try {
            usersFacadeRemote = new UsersFacade();
            priviligesFacadeRemote = new PriviligesFacade();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        Integer priviligeLevelValue = this.getPriviligeContext().getPriviligeLevel();
        priviligesFacadeRemote.setPriviligeLevel(priviligeLevelValue);
        usersFacadeRemote.setPriviligeLevel(priviligeLevelValue);
    }
}
