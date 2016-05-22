package pl.polsl.reservations.ejb.dao;

import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Equipment;

import javax.ejb.Local;
import java.util.List;

/**
 * @author matis
 */
@Local
public interface EquipmentDao extends AbstractDao<Equipment> {

    List<Equipment> getEquipmentByRoomNumber(int roomNumber);

}
