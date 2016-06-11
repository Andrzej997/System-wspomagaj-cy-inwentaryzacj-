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

    public static ClientContext getInstance() {
        return context;
    }

    public String getPrivilegeString(Long level) {
        return USER_TYPES[level.intValue() - 1];
    }

    public PrivilegeLevelDTO getUserPrivilegeLevel() {
        if (userDetails == null) {
            return null;
        }
        return userFacade.getUserPriviligeLevel();
    }

    public List<RoomDTO> getAccessibleRooms() {
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

    public Long getCurrentUserId() {
        if (userDetails == null) {
            return null;
        }
        return userDetails.getId();
    }

    public Boolean checkUserPrivilegesToAction(String requestedPrivilegeLevel) {
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

    public String getUsername() {
        if (userDetails == null) {
            return null;
        }
        return username;
    }

    public void setUsername(String username) {
        ClientContext.username = username;
        userDetails = userFacade.getUserDetails();
    }

    public Boolean isAdmin() {
        if (userDetails == null) {
            return false;
        }
        return userDetails.getPrivilegeLevel() == 1l;
    }

    public Boolean isStandardUser() {
        if (userDetails == null) {
            return false;
        }
        return userDetails.getPrivilegeLevel() == 6l;
    }

    public Boolean isGuest() {
        return username == null || username.isEmpty();
    }

    public Boolean canRequestHigherPrivilege() {
        if (isGuest() || isStandardUser()) {
            return false;
        }
        return !isAdmin();
    }

    public Boolean isTechnicalWorker() {
        if (userDetails == null) {
            return false;
        }
        return userDetails.getPrivilegeLevel() == 5l;
    }

    public Boolean canAcceptRequests() {
        if (!canRequestHigherPrivilege()) {
            return false;
        }
        return !isTechnicalWorker();
    }

    public Boolean canRequestLevel(String definedLevel) {
        if (!canRequestHigherPrivilege()) {
            return false;
        }
        Integer definedLevelValue = 4;
        for (int i = 0; i < 7; i++) {
            String type = USER_TYPES[i];
            if (type.equals(definedLevel)) {
                definedLevelValue = i + 1;
            }
        }
        if (userDetails == null) {
            return false;
        }
        int currentLevel = userDetails.getPrivilegeLevel().intValue();
        return definedLevelValue >= currentLevel - 1;
    }

    public void logout() {
        username = null;
        userDetails = null;
        privilegeLevel = 6l;
        accessibleRooms = null;
    }
}
