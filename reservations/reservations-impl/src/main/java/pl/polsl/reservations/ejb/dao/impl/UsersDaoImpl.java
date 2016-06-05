package pl.polsl.reservations.ejb.dao.impl;

import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import pl.polsl.reservations.ejb.dao.PriviligeLevelsDao;
import pl.polsl.reservations.ejb.dao.ReservationsDao;
import pl.polsl.reservations.ejb.dao.UsersDao;
import pl.polsl.reservations.entities.PriviligeLevels;
import pl.polsl.reservations.entities.Reservations;
import pl.polsl.reservations.entities.Users;
import pl.polsl.reservations.entities.Workers;
import pl.polsl.reservations.interceptors.TransactionalInterceptor;
import pl.polsl.reservations.logger.LoggerImpl;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class, TransactionalInterceptor.class})
@Stateful
@TransactionManagement(value = TransactionManagementType.BEAN)
public class UsersDaoImpl extends AbstractDaoImpl<Users> implements UsersDao {

    private static final long serialVersionUID = -8931746196880043035L;

    private ReservationsDao reservationsFacadeRemote;

    private PriviligeLevelsDao priviligeLevelsFacadeRemote;

    public UsersDaoImpl() throws NamingException {
        super(Users.class);
    }

    @Override
    public Boolean validateUser(String username, String password) {
        Query query = this.getEntityManager().createNamedQuery("validateUser", Users.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        List resultList = query.getResultList();
        return !resultList.isEmpty();
    }

    @Override
    public Boolean validateUserByEmail(String email, String password) {
        Query query = this.getEntityManager().createNamedQuery("validateUserByEmail", Users.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        List resultList = query.getResultList();
        return !resultList.isEmpty();
    }

    @Override
    public Long getUserPrivligeLevelByUsername(String username) {
        Query query = this.getEntityManager().createNamedQuery("getUserPrivligeLevelByUsername", PriviligeLevels.class);
        query.setParameter("username", username);
        try {
            PriviligeLevels singleResult = (PriviligeLevels) query.getSingleResult();
            return singleResult.getPriviligeLevel();
        } catch (NoResultException ex) {
            return new Long(6);
        }
    }

    @Override
    public Workers getWorkerByUsername(String username) {
        Query query = this.getEntityManager().createNamedQuery("getWorkerByUsername", Workers.class);
        query.setParameter("username", username);
        try {
            return (Workers) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Users getUserByUsername(String username) {
        Query query = this.getEntityManager().createNamedQuery("getUserByUsername", Users.class);
        query.setParameter("username", username);
        try {
            return (Users) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Users getUserByEmail(String email) {
        Query query = this.getEntityManager().createNamedQuery("getUserByEmail", Users.class);
        query.setParameter("email", email);
        try {
            return (Users) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Reservations> getReservationsCollectionById(Number id) {
        Users users = this.find(id);
        return users.getReservationsCollection();
    }

    @Override
    public void remove(Users entity) {
        getDependencies();

        Users user = this.find(entity.getId());
        List<Reservations> reservationsCollection = user.getReservationsCollection();
        for (Reservations reservation : reservationsCollection) {
            reservationsFacadeRemote.remove(reservation);
        }

        PriviligeLevels priviligeLevel = user.getPriviligeLevel();
        List<Users> usersCollection = priviligeLevel.getUsersCollection();
        usersCollection.remove(user);
        priviligeLevel.setUsersCollection(usersCollection);
        priviligeLevelsFacadeRemote.merge(priviligeLevel);

        super.remove(user);

    }

    @Override
    protected void getDependencies() {
        try {
            reservationsFacadeRemote = new ReservationsDaoImpl();
            priviligeLevelsFacadeRemote = new PriviligeLevelsDaoImpl();
            reservationsFacadeRemote.setUserContext(userContext);
            priviligeLevelsFacadeRemote.setUserContext(userContext);
        } catch (NamingException e) {
        }
    }
}
