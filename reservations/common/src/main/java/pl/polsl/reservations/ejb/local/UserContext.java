package pl.polsl.reservations.ejb.local;

import java.util.List;
import javax.persistence.EntityManager;

import pl.polsl.reservations.entities.Users;
import pl.polsl.reservations.privileges.PrivilegeLevelEnum;
import pl.polsl.reservations.entities.Priviliges;
import pl.polsl.reservations.privileges.PrivilegeEnum;

/**
 *
 * @author Mateusz Sojka
 * @version 1.0
 *
 * Interface represents user context
 */
public interface UserContext {

    /**
     * Method to initialize context
     *
     * @param privilegesList users' privileges list
     * @param user current user entity
     */
    void initialize(List<Priviliges> privilegesList, Users user);

    /**
     * Method checks that current user has privilege to something
     *
     * @param privilege PrivilegeEnum value
     * @return true if he has privilege
     */
    boolean checkPrivilege(PrivilegeEnum privilege);

    /**
     * Method returns entity manager with users privilege level
     *
     * @return new EntityManager object
     */
    EntityManager getEntityManager();

    /**
     * Set new PrivilegeLevel to current user
     *
     * @param privilegeLevel PrivilegeLevelEnum value
     */
    void setPrivilegeLevel(PrivilegeLevelEnum privilegeLevel);

    /**
     * Method returns current user privilege level
     *
     * @return PrivilegeLevelEnum value
     */
    PrivilegeLevelEnum getPrivilegeLevel();

    /**
     *
     * @return current user entity representation
     */
    Users getUser();

    /**
     * Set user entity to context
     *
     * @param user Users entity
     */
    void setUser(Users user);
}
