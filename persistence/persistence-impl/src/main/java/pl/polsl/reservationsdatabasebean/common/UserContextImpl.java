package pl.polsl.reservationsdatabasebean.common;

import pl.polsl.reservationsdatabasebeanremote.database.Priviliges;
import pl.polsl.reservationsdatabasebeanremote.database.common.PrivilegeEnum;
import pl.polsl.reservationsdatabasebeanremote.database.common.UserContext;

import javax.ejb.Stateful;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-05-19.
 */
@Stateful
public class UserContextImpl implements UserContext {

    EnumSet<PrivilegeEnum> privileges;

    public UserContextImpl() {}

    @Override
    public void initialize(List<Priviliges> privilegesList) {
        privileges = EnumSet.noneOf(PrivilegeEnum.class);
        for(Priviliges p : privilegesList) {
            try {
                PrivilegeEnum privilege =  PrivilegeEnum.getPrivilege(p.getPrivilegeName());
                privileges.add(privilege);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
