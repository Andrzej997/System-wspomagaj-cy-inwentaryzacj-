package pl.polsl.reservations.ejb.local;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;

import pl.polsl.reservations.entities.Users;
import pl.polsl.reservations.privileges.PrivilegeLevelEnum;
import pl.polsl.reservations.entities.Priviliges;
import pl.polsl.reservations.privileges.PrivilegeEnum;

/**
 * Created by Krzysztof Stręk on 2016-05-19.
 *
 * @version 1.0
 *
 * Class representing current user context
 */
public class UserContextImpl implements UserContext, Serializable {

    private static final long serialVersionUID = 6015626090282768654L;

    /**
     * List of current user privileges
     */
    private EnumSet<PrivilegeEnum> privileges;
    
    /**
     * Current user privilege level
     */
    private PrivilegeLevelEnum privilegeLevel;
    
    /**
     * Entity Manager 
     */
    private transient EntityManager entityManager;
    
    /**
     * Current user entity
     */
    private Users user;

    public UserContextImpl() {
        setPrivilegeLevel(PrivilegeLevelEnum.ADMIN);
    }

    /**
     * Method to initialize context
     *
     * @param privilegesList users' privileges list
     * @param user current user entity
     */
    @Override
    public void initialize(List<Priviliges> privilegesList, Users user) {
        privileges = EnumSet.noneOf(PrivilegeEnum.class);
        for (Priviliges p : privilegesList) {
            try {
                PrivilegeEnum privilege = PrivilegeEnum.getPrivilege(p.getPrivilegeName());
                privileges.add(privilege);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.user = user;
    }

    /**
     * Method checks that current user has privilege to something
     *
     * @param privilege PrivilegeEnum value
     * @return true if he has privilege
     */
    @Override
    public boolean checkPrivilege(PrivilegeEnum privilege) {
        return privileges != null && privileges.contains(privilege);
    }

    /**
     *
     * @return current user entity representation
     */
    @Override
    public Users getUser() {
        return user;
    }

    /**
     * Set user entity to context
     *
     * @param user Users entity
     */
    @Override
    public void setUser(Users user) {
        this.user = user;
    }

    /**
     * Method returns entity manager with users privilege level
     *
     * @return new EntityManager object
     */
    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Set new PrivilegeLevel to current user
     *
     * @param privilegeLevel PrivilegeLevelEnum value
     */
    @Override
    public final void setPrivilegeLevel(PrivilegeLevelEnum privilegeLevel) {
        this.privilegeLevel = privilegeLevel;
        this.entityManager = privilegeLevel.getEntityManager();
        this.entityManager.setFlushMode(FlushModeType.AUTO);
    }

    /**
     * Method returns current user privilege level
     *
     * @return PrivilegeLevelEnum value
     */
    @Override
    public PrivilegeLevelEnum getPrivilegeLevel() {
        return privilegeLevel;
    }

}
