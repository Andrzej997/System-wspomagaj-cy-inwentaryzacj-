package pl.polsl.reservations.userManagement;

import java.util.List;
import java.util.Map;
import javax.ejb.Remote;
import pl.polsl.reservations.dto.PrivilegeLevelDTO;
import pl.polsl.reservations.dto.UserDTO;

/**
 * Created by Krzysztof Stręk on 2016-05-09.
 */
@Remote
public interface UserManagementFacadeRemote {

    UserDTO getUserDetails(String userName);
    
    UserDTO getUserDetails(int userId);

    boolean assignUserToChief(String userName, String chiefName);

    boolean assignUserToRoom(String userName, int roomNumber);

    boolean changePrivilegeLevel(String userName, Long privilegeLevel);

    List<PrivilegeLevelDTO> getAllPrivilegeLevels();

    PrivilegeLevelDTO getUsersPrivilegeLevel(String userName);
    
    PrivilegeLevelDTO getUsersPrivilegeLevel(int userId);

    List<UserDTO> getUnderlings(String userName);
    
    List<UserDTO> getUnderlings(int userId);
}
