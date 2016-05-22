package pl.polsl.reservations.ejb.remote;

import java.util.List;
import java.util.Map;
import javax.ejb.Remote;
import pl.polsl.reservations.dto.DepartamentDTO;
import pl.polsl.reservations.dto.InstituteDTO;
import pl.polsl.reservations.dto.PrivilegeLevelDTO;
import pl.polsl.reservations.dto.UserDTO;

/**
 * Created by Krzysztof Stręk on 2016-05-09.
 */
@Remote
public interface UserManagementFacade {

    UserDTO getUserDetails(String userName);
    
    UserDTO getUserDetails(int userId);

    boolean assignUserToChief(String userName, String chiefName);

    boolean assignUserToRoom(String userName, int roomNumber);
    
    boolean assignUserToDepartament(String userName, Long departamentId);

    boolean changePrivilegeLevel(String userName, Long privilegeLevel);

    List<PrivilegeLevelDTO> getAllPrivilegeLevels();
    
    List<DepartamentDTO> getAllDepartaments();
    
    List<InstituteDTO> getAllInstitutes();

    PrivilegeLevelDTO getUsersPrivilegeLevel(String userName);
    
    PrivilegeLevelDTO getUsersPrivilegeLevel(int userId);

    List<UserDTO> getUnderlings(String userName);
    
    List<UserDTO> getUnderlings(int userId);
    
    boolean checkUserExistence(UserDTO user);
    
    boolean registerUser(UserDTO user, String password);
}
