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
 * @author Mateusz Sojka
 * @version 1.0
 *
 * Users data access object implementation
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

    /**
     * Method to validate user login
     *
     * @param username String username
     * @param password String password
     * @return true if user data exists
     */
    @Override
    public Boolean validateUser(String username, String password) {
        Query query = this.getEntityManager().createNamedQuery("validateUser", Users.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        List resultList = query.getResultList();
        return !resultList.isEmpty();
    }

    /**
     * Method to validate user login by email
     *
     * @param email String email
     * @param password String passwor
     * @return true if user data exists
     */
    @Override
    public Boolean validateUserByEmail(String email, String password) {
        Query query = this.getEntityManager().createNamedQuery("validateUserByEmail", Users.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        List resultList = query.getResultList();
        return !resultList.isEmpty();
    }

    /**
     * Method to get user privilege level by username
     *
     * @param username String username
     * @return Long privilege level value
     */
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

    /**
     * Method to get worker data by username
     *
     * @param username String username
     * @return Workers entity
     */
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

    /**
     * Method to get User by username
     *
     * @param username String username
     * @return Users entity
     */
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

    /**
     * Method to get user by email
     *
     * @param email String email
     * @return Users entity
     */
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

    /**
     * Method to get reservations which are made by user
     *
     * @param id user id
     * @return List of reservations
     */
    @Override
    public List<Reservations> getReservationsCollectionById(Number id) {
        Users users = this.find(id);
        return users.getReservationsCollection();
    }

    /**
     * Method used to remove entity from database
     *
     * @param entity entity to remove
     */
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
