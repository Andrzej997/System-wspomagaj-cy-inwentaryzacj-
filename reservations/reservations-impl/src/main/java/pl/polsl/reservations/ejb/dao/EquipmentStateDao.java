package pl.polsl.reservations.ejb.dao;

import java.util.List;
import javax.ejb.Local;
import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Equipment;
import pl.polsl.reservations.entities.EqupmentState;

/**
 * @author Mateusz Sojka
 * @version 1.0
 *
 * EquipmentState data access object interface
 */
@Local
public interface EquipmentStateDao extends AbstractDao<EqupmentState> {

    /**
     * Method to get equipment which has given state
     *
     * @param id state id
     * @return List of equipment
     */
    List<Equipment> getEquipmentCollectionById(Number id);
}
