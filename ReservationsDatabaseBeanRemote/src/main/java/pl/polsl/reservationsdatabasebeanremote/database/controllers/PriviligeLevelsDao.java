package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.PriviligeLevels;
import pl.polsl.reservationsdatabasebeanremote.database.Priviliges;
import pl.polsl.reservationsdatabasebeanremote.database.Users;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author matis
 */
@Remote
public interface PriviligeLevelsDao extends AbstractDao<PriviligeLevels> {

    List<Priviliges> getPriviligesCollectionById(Number id);

    List<Users> getUsersCollectionById(Number id);
    
    PriviligeLevels getPrivligeLevelsEntityByLevelValue(Long levelValue);
}
