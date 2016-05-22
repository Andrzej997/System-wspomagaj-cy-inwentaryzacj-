package pl.polsl.reservations.ejb.dao;

import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Equipment;
import pl.polsl.reservations.entities.EqupmentState;

import javax.ejb.Local;
import java.util.List;

/**
 * @author matis
 */
@Local
public interface EquipmentStateDao extends AbstractDao<EqupmentState> {

    List<Equipment> getEquipmentCollectionById(Number id);
}
