package pl.polsl.reservations.ejb.dao;

import java.util.List;
import javax.ejb.Local;
import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Departaments;
import pl.polsl.reservations.entities.Institutes;

/**
 * @author Mateusz Sojka
 * @version 1.0
 *
 * Institutes data access object interface
 */
@Local
public interface InstitutesDao extends AbstractDao<Institutes> {

    /**
     * Method to get institute by his name
     *
     * @param name String with institute name
     * @return Institutes entity
     */
    Institutes getInstituteByName(String name);

    /**
     * Method to get institute by his chief
     *
     * @param chiefId chief id
     * @return Institutes entity
     */
    Institutes getInstituteByChiefId(Long chiefId);

    /**
     * Method to get departaments assigned to institute
     *
     * @param id institute id
     * @return list of departaments
     */
    List<Departaments> getDepartamentsCollectionById(Number id);
}
