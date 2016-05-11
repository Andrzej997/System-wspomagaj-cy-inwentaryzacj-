package pl.polsl.reservations.roomManagement;

import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

/**
 * Created by Krzysztof Stręk on 2016-05-09.
 */
@Remote
public interface RoomManagementFacadeRemote {

    void addEquipment(int roomNumber, String name, int quantity, short stateId, short typeId);
    default void removeEquipment() {};
    List<Map<String, String>> getRoomsList();
    List<Map<String, String>> getRoomEquipment(int number);
    List<Map<String, String>> getEquipmentStates();
    List<Map<String, String>> getEquipmentTypes();
    default void assignUserToRoom() {};
    default void assignKeeperToRoom() {};
    default void addEquipmentType() {};
    default void removeEquipmentType() {};
    default void addEquipmentState() {};
    default void removeEquipmentState() {};
    default void findRoom() {};
}
