package pl.polsl.reservations.ejb.dao;

import java.util.List;
import javax.ejb.Local;
import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Equipment;

/**
 * @author matis
 */
@Local
public interface EquipmentDao extends AbstractDao<Equipment> {

    List<Equipment> getEquipmentByRoomNumber(int roomNumber);

}
