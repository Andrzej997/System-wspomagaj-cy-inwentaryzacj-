package pl.polsl.reservations.ejb.dao;

import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.PriviligeLevels;
import pl.polsl.reservations.entities.Priviliges;
import pl.polsl.reservations.entities.Users;

import javax.ejb.Local;
import java.util.List;

/**
 * @author matis
 */
@Local
public interface PriviligeLevelsDao extends AbstractDao<PriviligeLevels> {

    List<Priviliges> getPriviligesCollectionById(Number id);

    List<Users> getUsersCollectionById(Number id);
    
    PriviligeLevels getPrivligeLevelsEntityByLevelValue(Long levelValue);
}
