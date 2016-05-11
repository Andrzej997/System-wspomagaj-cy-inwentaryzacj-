package pl.polsl.reservationsdatabasebean.controllers;

import java.util.List;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.persistence.Query;
import pl.polsl.reservationsdatabasebean.logger.LoggerImpl;
import pl.polsl.reservationsdatabasebeanremote.database.PriviligeLevels;
import pl.polsl.reservationsdatabasebeanremote.database.Users;
import pl.polsl.reservationsdatabasebeanremote.database.Workers;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.UsersFacadeRemote;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class})
@Stateful
public class UsersFacade extends AbstractFacade<Users> implements UsersFacadeRemote {

    private static final long serialVersionUID = -8931746196880043035L;

    public UsersFacade() throws NamingException {
        super(Users.class);
    }

    @Override
    public Boolean validateUser(String username, String password){
        Query query = this.getEntityManager().createNamedQuery("validateUser", Users.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        List resultList = query.getResultList();
        return !resultList.isEmpty();
    }

    @Override
    public Boolean validateUserByEmail(String email, String password){
        Query query = this.getEntityManager().createNamedQuery("validateUserByEmail", Users.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        List resultList = query.getResultList();
        return !resultList.isEmpty();
    }

    @Override
    public Long getUserPrivligeLevelByUsername(String username){
        Query query = this.getEntityManager().createNamedQuery("getUserPrivligeLevelByUsername", PriviligeLevels.class);
        query.setParameter("username", username);
        PriviligeLevels singleResult = (PriviligeLevels) query.getSingleResult();
        return singleResult.getPriviligeLevel();
    }

    @Override
    public Workers getWorkerByUsername(String username){
        Query query = this.getEntityManager().createNamedQuery("getWorkerByUsername", Workers.class);
        query.setParameter("username", username);
        return (Workers) query.getSingleResult();
    }
    
    @Override
    public Users getUserByUsername(String username){
        Query query = this.getEntityManager().createNamedQuery("getUserByUsername", Users.class);
        query.setParameter("username", username);
        return (Users) query.getSingleResult();
    }
    
    @Override
    public Users getUserByEmail(String email){
        Query query = this.getEntityManager().createNamedQuery("getUserByEmail", Users.class);
        query.setParameter("email", email);
        return (Users) query.getSingleResult();
    }
}
