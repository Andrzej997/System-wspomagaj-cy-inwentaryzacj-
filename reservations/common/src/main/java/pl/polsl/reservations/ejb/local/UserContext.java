package pl.polsl.reservations.ejb.local;

import java.util.List;
import javax.persistence.EntityManager;

import pl.polsl.reservations.entities.Users;
import pl.polsl.reservations.privileges.PrivilegeLevelEnum;
import pl.polsl.reservations.entities.Priviliges;
import pl.polsl.reservations.privileges.PrivilegeEnum;

/**
 *
 * @author matis
 */
public interface UserContext {

    void initialize(List<Priviliges> privilegesList, Users user);

    boolean checkPrivilege(PrivilegeEnum privilege);

    EntityManager getEntityManager();

    void setPrivilegeLevel(PrivilegeLevelEnum privilegeLevel);

    PrivilegeLevelEnum getPrivilegeLevel();

    Users getUser();

    void setUser(Users user);
}
