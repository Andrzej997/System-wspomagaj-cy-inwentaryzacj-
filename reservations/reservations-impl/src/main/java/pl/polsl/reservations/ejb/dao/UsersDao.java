package pl.polsl.reservations.ejb.dao;

import java.util.List;
import javax.ejb.Local;
import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Reservations;
import pl.polsl.reservations.entities.Users;
import pl.polsl.reservations.entities.Workers;

/**
 * @author Mateusz Sojka
 * @version 1.0
 *
 * Users data access object interface
 */
@Local
public interface UsersDao extends AbstractDao<Users> {

    /**
     * Method to validate user login
     *
     * @param username String username
     * @param password String password
     * @return true if user data exists
     */
    Boolean validateUser(String username, String password);

    /**
     * Method to validate user login by email
     *
     * @param email String email
     * @param password String passwor
     * @return true if user data exists
     */
    Boolean validateUserByEmail(String email, String password);

    /**
     * Method to get user privilege level by username
     *
     * @param username String username
     * @return Long privilege level value
     */
    Long getUserPrivligeLevelByUsername(String username);

    /**
     * Method to get worker data by username
     *
     * @param username String username
     * @return Workers entity
     */
    Workers getWorkerByUsername(String username);

    /**
     * Method to get User by username
     *
     * @param username String username
     * @return Users entity
     */
    Users getUserByUsername(String username);

    /**
     * Method to get user by email
     *
     * @param email String email
     * @return Users entity
     */
    Users getUserByEmail(String email);

    /**
     * Method to get reservations which are made by user
     * @param id user id
     * @return List of reservations
     */
    List<Reservations> getReservationsCollectionById(Number id);
}
