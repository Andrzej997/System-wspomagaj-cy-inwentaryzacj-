package pl.polsl.reservations.ejb.dao;

import java.util.List;
import javax.ejb.Local;
import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.PriviligeLevels;
import pl.polsl.reservations.entities.Priviliges;
import pl.polsl.reservations.entities.Users;

/**
 * @author matis
 */
@Local
public interface PriviligeLevelsDao extends AbstractDao<PriviligeLevels> {

    List<Priviliges> getPriviligesCollectionById(Number id);

    List<Users> getUsersCollectionById(Number id);
    
    PriviligeLevels getPrivligeLevelsEntityByLevelValue(Long levelValue);
}
