package pl.polsl.reservations.ejb.dao;

import java.util.List;
import javax.ejb.Local;
import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.PriviligeLevels;
import pl.polsl.reservations.entities.Priviliges;
import pl.polsl.reservations.entities.Users;

/**
 * @author Mateusz Sojka
 * @version 1.0
 * 
 * PrivilegeLevels data access object interface
 */
@Local
public interface PriviligeLevelsDao extends AbstractDao<PriviligeLevels> {

    
    /**
     * Method to get privileges collection by privilege level id
     * @param id privilege level id
     * @return list of Privileges
     */
    List<Priviliges> getPriviligesCollectionById(Number id);

    /**
     * Method to get users which has given privilege level
     * @param id privilege level id
     * @return list of users
     */
    List<Users> getUsersCollectionById(Number id);

    /**
     * Method to get privilege level entity by level value 
     * @param levelValue Long with privilege level value
     * @return PrivilegeLevels enitity
     */
    PriviligeLevels getPrivligeLevelsEntityByLevelValue(Long levelValue);
}
