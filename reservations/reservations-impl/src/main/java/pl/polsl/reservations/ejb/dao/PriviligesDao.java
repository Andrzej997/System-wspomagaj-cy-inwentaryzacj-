package pl.polsl.reservations.ejb.dao;

import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.PriviligeLevels;
import pl.polsl.reservations.entities.Priviliges;

import javax.ejb.Local;
import java.util.List;

/**
 * @author matis
 */
@Local
public interface PriviligesDao extends AbstractDao<Priviliges> {

    List<PriviligeLevels> getPriviligeLevelsCollectionById(Number id);
}
