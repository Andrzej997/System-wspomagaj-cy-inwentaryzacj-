package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Equipment;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author matis
 */
@Remote
public interface EquipmentFacadeRemote extends AbstractFacadeRemote<Equipment> {

    List<Equipment> getEquipmentByRoomNumber(int roomNumber);

}
