package pl.polsl.reservations.ejb.dao;

import java.util.List;
import javax.ejb.Local;
import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.PriviligeLevels;
import pl.polsl.reservations.entities.Priviliges;

/**
 * @author matis
 */
@Local
public interface PriviligesDao extends AbstractDao<Priviliges> {

    List<PriviligeLevels> getPriviligeLevelsCollectionById(Number id);
}
