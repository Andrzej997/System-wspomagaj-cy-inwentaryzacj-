package pl.polsl.reservations.userManagement;

import java.util.Map;
import javax.ejb.Remote;

/**
 * Created by Krzysztof Stręk on 2016-05-09.
 */
@Remote
public interface UserManagementFacadeRemote {
    Map<String, String> getUserDetails(String userName);
    
    boolean assignUserToChief(String userName, String chiefName);
    
    boolean assignUserToRoom(String userName, int roomNumber);
}
