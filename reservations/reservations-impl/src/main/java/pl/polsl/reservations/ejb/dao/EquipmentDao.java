package pl.polsl.reservations.ejb.dao;

import java.util.List;
import javax.ejb.Local;
import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Equipment;

/**
 * @author Mateusz Sojka
 * @version 1.0
 *
 * Equipment data access object interface
 */
@Local
public interface EquipmentDao extends AbstractDao<Equipment> {

    /**
     * Method to get room equipment by room number
     *
     * @param roomNumber int with room number
     * @return list of rooms' equipment
     */
    List<Equipment> getEquipmentByRoomNumber(int roomNumber);

}
