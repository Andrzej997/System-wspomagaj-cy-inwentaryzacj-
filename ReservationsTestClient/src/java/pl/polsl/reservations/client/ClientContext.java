package pl.polsl.reservations.client;

import pl.polsl.reservations.dto.PrivilegeLevelDTO;
import pl.polsl.reservations.ejb.remote.UserManagementFacade;

/**
 *
 * @author matis
 */
public class ClientContext {
    
    private static final String[] USER_TYPES = new String[] {"ADMIN", "INSTITUTE_CHIEF",
    "DEPARTAMENT_CHIEF", "TECHNICAL_CHIEF", "TECHNICAL_WORKER", "STANDARD_USER", "TESTER"};
    private static final UserManagementFacade userManagementFacade = 
            (UserManagementFacade) Lookup.getRemote("UserManagementFacade");
    
    public ClientContext(){
    }
    
    public static PrivilegeLevelDTO getUserPrivilegeLevel(){
        return userManagementFacade.getUsersPrivilegeLevel("");
    }
    
    public static Boolean checkUserPrivilegesToAction(String requestedPrivilegeLevel){
        Long requestedLevel = 0l;
        for(int i = 0; i<USER_TYPES.length; i++){
            if(USER_TYPES[i].equals(requestedPrivilegeLevel)){
                requestedLevel=new Long(i+1);
            }
        }
        PrivilegeLevelDTO usersPrivilegeLevel = userManagementFacade.getUsersPrivilegeLevel("");
        Long privilegeLevel = usersPrivilegeLevel.getPrivilegeLevel();
        return privilegeLevel >= requestedLevel;
    }
    
    
}
