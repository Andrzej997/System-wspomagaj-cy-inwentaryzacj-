package pl.polsl.reservations.ejb.dao;


import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Equipment;
import pl.polsl.reservations.entities.EquipmentType;

import javax.ejb.Local;
import java.util.List;

/**
 * @author matis
 */
@Local
public interface EquipmentTypeDao extends AbstractDao<EquipmentType> {

    List<Equipment> getEquipmentCollectionById(Number id);
}