package pl.polsl.reservations.ejb.dao;

import java.util.List;
import javax.ejb.Local;
import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Equipment;
import pl.polsl.reservations.entities.EquipmentType;

/**
 * @author Mateusz Sojka
 * @version 1.0
 *
 * EquipmentType data access object interface
 */
@Local
public interface EquipmentTypeDao extends AbstractDao<EquipmentType> {

    /**
     * Method to get equipment which has given type
     *
     * @param id type id
     * @return list of equipment
     */
    List<Equipment> getEquipmentCollectionById(Number id);
}
