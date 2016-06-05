package pl.polsl.reservations.client;

import pl.polsl.reservations.dto.PrivilegeLevelDTO;
import pl.polsl.reservations.ejb.remote.UserFacade;

/**
 *
 * @author matis
 */
public class ClientContext {

    private static final String[] USER_TYPES = new String[]{"ADMIN", "INSTITUTE_CHIEF",
        "DEPARTAMENT_CHIEF", "TECHNICAL_CHIEF", "TECHNICAL_WORKER", "STANDARD_USER", "TESTER"};
    private static final UserFacade userFacade
            = (UserFacade) Lookup.getRemote("UserFacade");

    private static String username;

    public ClientContext() {

    }

    public static PrivilegeLevelDTO getUserPrivilegeLevel() {
        return userFacade.getUserPriviligeLevel();
    }

    public static Boolean checkUserPrivilegesToAction(String requestedPrivilegeLevel) {
        Long requestedLevel = 0l;
        for (int i = 0; i < USER_TYPES.length; i++) {
            if (USER_TYPES[i].equals(requestedPrivilegeLevel)) {
                requestedLevel = new Long(i + 1);
            }
        }
        PrivilegeLevelDTO usersPrivilegeLevel = userFacade.getUserPriviligeLevel();
        Long privilegeLevel = usersPrivilegeLevel.getPrivilegeLevel();
        return privilegeLevel <= requestedLevel;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        ClientContext.username = username;
    }

}
