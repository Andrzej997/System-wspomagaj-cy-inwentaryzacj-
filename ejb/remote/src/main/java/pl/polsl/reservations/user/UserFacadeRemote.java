package pl.polsl.reservations.user;

import java.util.Map;
import javax.ejb.Remote;

/**
 * Created by Krzysztof Stręk on 2016-05-07.
 */
@Remote
public interface UserFacadeRemote {

    public boolean login(String nameOrEmail, String password);
    
    public boolean logout();
    
    public Long getUserPrivilege();
    
    public boolean changePassword(String oldPassword, String newPassword);
    
    public Map<String, String> getUserDetails();
}
