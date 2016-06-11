package pl.polsl.reservations.client;

import java.util.List;
import pl.polsl.reservations.dto.PrivilegeLevelDTO;
import pl.polsl.reservations.dto.RoomDTO;
import pl.polsl.reservations.dto.UserDTO;
import pl.polsl.reservations.ejb.remote.RoomManagementFacade;
import pl.polsl.reservations.ejb.remote.UserFacade;

/**
 *
 * @author matis
 */
public class ClientContext {

    private static final String[] USER_TYPES = new String[]{"ADMIN", "INSTITUTE_CHIEF",
        "DEPARTAMENT_CHIEF", "TECHNICAL_CHIEF", "TECHNICAL_WORKER", "STANDARD_USER", "TESTER"};
    private static final UserFacade userFacade = Lookup.getUserFacade();
    private static final RoomManagementFacade roomManagementFacade = Lookup.getRoomManagementFacade();

    private static UserDTO userDetails;
    private static String username;
    private static Long privilegeLevel;
    private static List<RoomDTO> accessibleRooms;
    private static final ClientContext context = new ClientContext();

    private ClientContext() {
        userDetails = userFacade.getUserDetails();
        if (userDetails != null) {
            privilegeLevel = userDetails.getPrivilegeLevel();
        }
    }

    public static PrivilegeLevelDTO getUserPrivilegeLevel() {
        if (userDetails == null) {
            return null;
        }
        return userFacade.getUserPriviligeLevel();
    }

    public static List<RoomDTO> getAccessibleRooms() {
        if (userDetails == null) {
            return null;
        }
        if (checkUserPrivilegesToAction("ADMIN")) {
            accessibleRooms = roomManagementFacade.getRoomsList();
        } else if (checkUserPrivilegesToAction("INSTITUTE_CHIEF")) {
            accessibleRooms = roomManagementFacade.getInstituteRooms(userDetails.getId());
        } else if (checkUserPrivilegesToAction("DEPARTAMENT_CHIEF")) {
            accessibleRooms = roomManagementFacade.getDepartamentRooms(userDetails.getId());
        } else if (checkUserPrivilegesToAction("TECHNICAL_CHIEF")) {
            accessibleRooms = roomManagementFacade.getDepartamentRooms(userDetails.getId());
        } else if (checkUserPrivilegesToAction("TECHNICAL_WORKER")) {
            accessibleRooms = roomManagementFacade.getDepartamentRooms(userDetails.getId());
        } else if (checkUserPrivilegesToAction("STANDARD_USER")) {
            accessibleRooms = null;
        } else if (checkUserPrivilegesToAction("TESTER")) {
            accessibleRooms = roomManagementFacade.getRoomsList();
        }
        return accessibleRooms;
    }

    public static Long getCurrentUserId() {
        if (userDetails == null) {
            return null;
        }
        return userDetails.getId();
    }

    public static Boolean checkUserPrivilegesToAction(String requestedPrivilegeLevel) {
        if (userDetails == null) {
            return false;
        }
        Long requestedLevel = 0l;
        for (int i = 0; i < USER_TYPES.length; i++) {
            if (USER_TYPES[i].equals(requestedPrivilegeLevel)) {
                requestedLevel = new Long(i + 1);
            }
        }
        return privilegeLevel <= requestedLevel;
    }

    public static String getUsername() {
        if (userDetails == null) {
            return null;
        }
        return username;
    }

    public static void setUsername(String username) {
        ClientContext.username = username;
    }

    public static Boolean isAdmin() {
        return checkUserPrivilegesToAction("ADMIN");
    }

    public static Boolean isStandardUser() {
        return checkUserPrivilegesToAction("STANDARD_USER");
    }

    public static Boolean isGuest() {
        return username.isEmpty();
    }
    
    public static Boolean canRequestHigherPrivilege(){
        if(isGuest() || isStandardUser()){
            return false;
        }
        return !isAdmin();
    }

}
