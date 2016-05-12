package pl.polsl.reservations.roomManagement;

import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

/**
 * Created by Krzysztof Stręk on 2016-05-09.
 */
@Remote
public interface RoomManagementFacadeRemote {

    void addEquipment(int roomId, String name, int quantity, short stateId, short typeId);
    void removeEquipment(int equipmentId);
    void moveEquipment(int equipmentId, int roomToId);
    List<Map<String, String>> getRoomsList();
    List<Map<String, String>> getRoomEquipment(int roomId);
    List<Map<String, String>> getEquipmentStates();
    List<Map<String, String>> getEquipmentTypes();
    void assignUserToRoom(int roomId, int workerId);
    void assignKeeperToRoom(int roomId, int workerId);
    Map<String, String> getRoomKeeper(int roomId);
    void addEquipmentType(String shortDescription, String longDescription);
    void removeEquipmentType(int typeId);
    void addEquipmentState(String definition);
    void removeEquipmentState(int stateId);

    //TODO implementacja jak będzie wiadomo jak ma działać
    default void findRoom() {};
}
