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
 */
public class UserContextImpl implements UserContext, Serializable{

    private static final long serialVersionUID = 6015626090282768654L;

    private EnumSet<PrivilegeEnum> privileges;
    private PrivilegeLevelEnum privilegeLevel;
    private EntityManager entityManager;
    private Users user;


    public UserContextImpl() {
        setPrivilegeLevel(PrivilegeLevelEnum.ADMIN);
    }

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

    @Override
    public boolean checkPrivilege(PrivilegeEnum privilege) {
        return privileges != null && privileges.contains(privilege);
    }

    @Override
    public Users getUser() {
        return user;
    }
    
    @Override
    public void setUser(Users user){
        this.user = user;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public final void setPrivilegeLevel(PrivilegeLevelEnum privilegeLevel) {
        this.privilegeLevel = privilegeLevel;
        this.entityManager = privilegeLevel.getEntityManager();
        this.entityManager.setFlushMode(FlushModeType.COMMIT);
    }

    @Override
    public PrivilegeLevelEnum getPrivilegeLevel() {
        return privilegeLevel;
    }

}
