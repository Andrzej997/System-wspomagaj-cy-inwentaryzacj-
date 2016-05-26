package pl.polsl.reservations.ejb.dao;

import java.util.List;
import javax.ejb.Local;
import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Equipment;
import pl.polsl.reservations.entities.EqupmentState;

/**
 * @author matis
 */
@Local
public interface EquipmentStateDao extends AbstractDao<EqupmentState> {

    List<Equipment> getEquipmentCollectionById(Number id);
}
