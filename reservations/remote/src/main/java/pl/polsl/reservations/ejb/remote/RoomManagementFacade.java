package pl.polsl.reservations.ejb.remote;

import pl.polsl.reservations.dto.*;

import javax.ejb.Remote;
import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-05-09.
 */
@Remote
public interface RoomManagementFacade {

    void addEquipment(int roomId, String name, int quantity, short stateId, short typeId);
    void removeEquipment(int equipmentId);
    void moveEquipment(int equipmentId, int roomToId);
    List<RoomDTO> getRoomsList();
    List<EquipmentDTO> getRoomEquipment(int roomId);
    List<EquipmentStateDTO> getEquipmentStates();
    List<EquipmentTypeDTO> getEquipmentTypes();
    void assignUserToRoom(int roomId, int workerId);
    void assignKeeperToRoom(int roomId, int workerId);
    UserDTO getRoomKeeper(int roomId);
    void addEquipmentType(String shortDescription, String longDescription);
    void removeEquipmentType(int typeId);
    void addEquipmentState(String definition);
    void removeEquipmentState(int stateId);
    List<RoomDTO> getRoomsWithNumberOfSeatsHigherEqualThan(Number numberOfSeats);

    //TODO implementacja jak będzie wiadomo jak ma działać
    default void findRoom() {
    }
}
