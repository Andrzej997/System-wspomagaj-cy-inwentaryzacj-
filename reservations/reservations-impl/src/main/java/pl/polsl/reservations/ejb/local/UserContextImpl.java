package pl.polsl.reservations.ejb.local;

import java.util.EnumSet;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import pl.polsl.reservations.entities.PrivilegeLevelEnum;
import pl.polsl.reservations.entities.Priviliges;
import pl.polsl.reservations.privileges.PrivilegeEnum;

/**
 * Created by Krzysztof Stręk on 2016-05-19.
 */
public class UserContextImpl implements UserContext{

    private EnumSet<PrivilegeEnum> privileges;
    private PrivilegeLevelEnum privilegeLevel;
    private EntityManager entityManager;


    public UserContextImpl() {
        setPrivilegeLevel(PrivilegeLevelEnum.ADMIN);
    }

    @Override
    public void initialize(List<Priviliges> privilegesList) {
        privileges = EnumSet.noneOf(PrivilegeEnum.class);
        for (Priviliges p : privilegesList) {
            try {
                PrivilegeEnum privilege = PrivilegeEnum.getPrivilege(p.getPrivilegeName());
                privileges.add(privilege);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean checkPrivilege(PrivilegeEnum privilege) {
        return privileges.contains(privilege);
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
