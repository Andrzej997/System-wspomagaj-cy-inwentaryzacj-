package pl.polsl.reservations.ejb.remote;

import java.util.List;
import javax.ejb.Remote;
import pl.polsl.reservations.dto.*;

/**
 * Created by Krzysztof Stręk on 2016-05-09.
 */
@Remote
public interface RoomManagementFacade extends AbstractBusinessFacade{

    Boolean addEquipment(int roomId, String name, int quantity, short stateId, short typeId) throws UnauthorizedAccessException;
    void editEquipment(Long equipmentId, String name, int quantity, short stateId, short typeId);
    void removeEquipment(int equipmentId) throws UnauthorizedAccessException;
    void moveEquipment(int equipmentId, int roomToId) throws UnauthorizedAccessException;
    List<RoomDTO> getRoomsList();
    RoomDTO getRoom(int roomNumber);
    Boolean addRoom(int roomNumber, Long keeperId, Long departamentId, Long roomTypeId, int numberOfSeats);
    List<EquipmentDTO> getRoomEquipment(int roomId);
    List<EquipmentDTO> getDepartmentEquipment(int departmentId);
    List<EquipmentStateDTO> getEquipmentStates();
    List<EquipmentTypeDTO> getEquipmentTypes();
    void assignUserToRoom(int roomId, int workerId);
    void assignKeeperToRoom(int roomId, int workerId);
    UserDTO getRoomKeeper(int roomId);
    Boolean addEquipmentType(String shortDescription, String longDescription);
    void removeEquipmentType(int typeId);
    Boolean addEquipmentState(String definition);
    void removeEquipmentState(int stateId);
    List<RoomDTO> getRoomsWithNumberOfSeatsHigherEqualThan(Number numberOfSeats);

    //TODO implementacja jak będzie wiadomo jak ma działać
    default void findRoom() {
    }

    List<RoomTypesDTO> getRoomTypes();
}
