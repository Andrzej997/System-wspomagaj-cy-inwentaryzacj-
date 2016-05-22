package pl.polsl.reservations.ejb.local;

import pl.polsl.reservations.entities.PrivilegeLevelEnum;
import pl.polsl.reservations.entities.Priviliges;
import pl.polsl.reservations.privileges.PrivilegeEnum;

import javax.ejb.Local;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-05-19.
 */
@Local
public interface UserContext {
    void initialize(List<Priviliges> privileges);
    boolean checkPrivilege(PrivilegeEnum privilege);
    EntityManager getEntityManager();
    void setPrivilegeLevel(PrivilegeLevelEnum privilegeLevel);
    PrivilegeLevelEnum getPrivilegeLevel();
}
