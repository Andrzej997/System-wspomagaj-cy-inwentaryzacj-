package pl.polsl.reservations.ejb.dao;

import java.util.List;
import javax.ejb.Local;
import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Equipment;
import pl.polsl.reservations.entities.EquipmentType;

/**
 * @author matis
 */
@Local
public interface EquipmentTypeDao extends AbstractDao<EquipmentType> {

    List<Equipment> getEquipmentCollectionById(Number id);
}
