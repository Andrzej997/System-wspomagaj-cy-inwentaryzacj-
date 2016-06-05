package pl.polsl.reservations.ejb.remote;

import java.util.List;
import javax.ejb.Remote;
import pl.polsl.reservations.dto.PrivilegeLevelDTO;
import pl.polsl.reservations.dto.UserDTO;

/**
 * Created by Krzysztof Stręk on 2016-05-07.
 */
@Remote
public interface UserFacade extends AbstractBusinessFacade{

    public boolean login(String nameOrEmail, String password);
    
    public boolean logout();
    
    public PrivilegeLevelDTO getUserPriviligeLevel();
    
    public boolean changePassword(String oldPassword, String newPassword);
    
    public UserDTO getUserDetails();
    
    public boolean changeUserDetails(UserDTO user);
    
    public boolean loginAsGuest();

    public void removeCertificate(String certifiate);
    
    public PrivilegeLevelDTO getObtainablePrivilegeLevel();
    
    public boolean requestHigherPrivilegeLevel(String reason);
    
    public boolean isRequestingHigherPrivilegeLevel();
    
    public List<UserDTO> getUsersWithLowerPrivilegeLevel();
}
