package pl.polsl.reservations.ejb.dao;

import java.util.List;
import javax.ejb.Local;
import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.PriviligeLevels;
import pl.polsl.reservations.entities.Priviliges;

/**
 * @author Mateusz Sojka
 * @version 1.0
 * 
 * Privilege data access object interface
 */
@Local
public interface PriviligesDao extends AbstractDao<Priviliges> {

    /**
     * Method to get privilege levels which has given privilege
     * @param id privilege id
     * @return list of privilege levels
     */
    List<PriviligeLevels> getPriviligeLevelsCollectionById(Number id);
}
