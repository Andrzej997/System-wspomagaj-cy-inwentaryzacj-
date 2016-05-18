package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Equipment;
import pl.polsl.reservationsdatabasebeanremote.database.EqupmentState;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author matis
 */
@Remote
public interface EquipmentStateDao extends AbstractDao<EqupmentState> {

    List<Equipment> getEquipmentCollectionById(Number id);
}
