package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Equipment;
import pl.polsl.reservationsdatabasebeanremote.database.EquipmentType;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author matis
 */
@Remote
public interface EquipmentTypeDao extends AbstractDao<EquipmentType> {

    List<Equipment> getEquipmentCollectionById(Number id);
}