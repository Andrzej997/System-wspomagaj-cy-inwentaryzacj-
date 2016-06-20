package pl.polsl.reservations.ejb.remote;

import java.util.List;
import javax.ejb.Remote;
import pl.polsl.reservations.dto.*;

/**
 * Created by Krzysztof Stręk on 2016-05-09.
 *
 * @version 1.0
 *
 * Room management facade remote interface to manage rooms at university
 */
@Remote
public interface RoomManagementFacade extends AbstractBusinessFacade {

    /**
     * Method to add equipment to room
     *
     * @param roomId Int id of room
     * @param name Equipment name
     * @param quantity Equipment quantity
     * @param stateId EquipmentState id
     * @param typeId EquipmentType id
     * @return true if succesfully added
     * @throws UnauthorizedAccessException when user has no access
     */
    Boolean addEquipment(int roomId, String name, int quantity, short stateId, short typeId) throws UnauthorizedAccessException;

    /**
     * Method to edit equimpent
     *
     * @param equipmentId selected Equipment id
     * @param name new equipment name
     * @param quantity new equipment quantity
     * @param stateId new EquipmentState id
     * @param typeId new EquipmentType id
     */
    void editEquipment(Long equipmentId, String name, int quantity, short stateId, short typeId);

    /**
     * Method to remove Equipment
     *
     * @param equipmentId Equipment id to remove
     * @throws UnauthorizedAccessException when user has no access
     */
    void removeEquipment(int equipmentId) throws UnauthorizedAccessException;

    /**
     * Method to move equipment to another room
     *
     * @param equipmentId selected equipment id
     * @param roomToId new room id
     * @throws UnauthorizedAccessException when user has no access
     */
    void moveEquipment(int equipmentId, int roomToId) throws UnauthorizedAccessException;

    /**
     * Method to obtain all rooms list
     *
     * @return List of all rooms at university
     */
    List<RoomDTO> getRoomsList();

    /**
     * Method to ger roomDTO by room number
     *
     * @param roomNumber Integer with room number
     * @return RoomDTO object
     */
    RoomDTO getRoom(int roomNumber);

    /**
     * Method to add room
     *
     * @param roomNumber new room number
     * @param keeperId rooms' keeper id of Workers
     * @param departamentId rooms' departament id of Departaments
     * @param roomTypeId room type id of RoomTypes
     * @param numberOfSeats room number of seats
     * @return true if success
     */
    Boolean addRoom(int roomNumber, Long keeperId, Long departamentId, Long roomTypeId, int numberOfSeats);

    /**
     * Metho to ger rooms' equipment
     *
     * @param roomId room id
     * @return List of rooms' equipment
     */
    List<EquipmentDTO> getRoomEquipment(int roomId);

    /**
     * Method to get departaments' equipment
     *
     * @param departmentId departament id
     * @return List of departaments' equipment
     */
    List<EquipmentDTO> getDepartmentEquipment(int departmentId);

    /**
     * Method to get equipment state dictionary values
     *
     * @return List of all equipment states
     */
    List<EquipmentStateDTO> getEquipmentStates();

    /**
     * Method to get equipment type dictionary values
     *
     * @return List of all equipment types
     */
    List<EquipmentTypeDTO> getEquipmentTypes();

    /**
     * Method to assign user to room
     *
     * @param roomId room id
     * @param workerId worker id
     */
    void assignUserToRoom(int roomId, int workerId);

    /**
     * Method to assign keeper to room
     *
     * @param roomId room id
     * @param workerId worker id
     */
    void assignKeeperToRoom(int roomId, int workerId);

    /**
     * Method to assign room to departament
     *
     * @param roomId room id
     * @param departmentId departament id
     */
    void assignRoomToDepartament(Long roomId, Long departmentId);

    /**
     * *
     * Method to get rooms' keeper
     *
     * @param roomId room id
     * @return UserDTO with rooms' keeper data
     */
    UserDTO getRoomKeeper(int roomId);

    /**
     * Method to add new equipment type to dictionary
     *
     * @param shortDescription String with type description
     * @param longDescription String with type long description
     * @return true if succesfully added
     */
    Boolean addEquipmentType(String shortDescription, String longDescription);

    /**
     * Method to remove equipment type from dictionary
     *
     * @param typeId equipment type id
     */
    void removeEquipmentType(int typeId);

    /**
     * Method to add new equipment state to dictionary
     *
     * @param definition String with state definition
     * @return true if succesfully added
     */
    Boolean addEquipmentState(String definition);

    /**
     * Method to remove equipment state from dictionary
     *
     * @param stateId equipment state id
     */
    void removeEquipmentState(int stateId);

    /**
     * Method to get rooms with seats quantity higher than given value
     *
     * @param numberOfSeats minimal seats quantity
     * @return List of rooms
     */
    List<RoomDTO> getRoomsWithNumberOfSeatsHigherEqualThan(Number numberOfSeats);

    /**
     * Method to get room type dictionary values
     *
     * @return list of room types
     */
    List<RoomTypesDTO> getRoomTypes();

    /**
     * Method to ger workers assinged to room
     *
     * @param roomId room id
     * @return List of users
     */
    public List<UserDTO> getRoomWorkers(Long roomId);

    /**
     * Method to get institute rooms
     *
     * @param userId user id assigned to institute
     * @return List of rooms
     */
    public List<RoomDTO> getInstituteRooms(Long userId);

    /**
     * Method to get departament rooms
     *
     * @param userId user id assigned to departament
     * @return List of rooms
     */
    public List<RoomDTO> getDepartamentRooms(Long userId);

    /**
     * Method to new room type value to dictionary
     *
     * @param roomTypesDTO new room type
     * @return true if success
     */
    public boolean addRoomType(RoomTypesDTO roomTypesDTO);

    /**
     * Method to remove room type from dictionary
     *
     * @param roomTypesDTO room type to remove
     * @return true if success
     */
    public boolean removeRoomType(RoomTypesDTO roomTypesDTO);

    /**
     * Method to change room type dictionary data
     *
     * @param roomTypesDTO room type with new data
     * @return true if success
     */
    public boolean editRoomType(RoomTypesDTO roomTypesDTO);

    /**
     * Method to add new departament
     *
     * @param departamentDTO new departament
     * @param chiefID worker id which will be new departaments' chief
     * @param instituteID institute id
     * @return true if success
     */
    public boolean addDepartament(DepartamentDTO departamentDTO, Long chiefID, Long instituteID);

    /**
     * Method to remove departament
     *
     * @param departamentDTO departament to remove
     * @return true if sucess
     */
    public boolean removeDepartament(DepartamentDTO departamentDTO);

    /**
     * Method to change departaments' data
     *
     * @param departamentDTO departament data to change
     * @param chiefID new departaments' chief id
     * @param instituteID new institute id
     * @return true if sucess
     */
    public boolean editDepartament(DepartamentDTO departamentDTO, Long chiefID, Long instituteID);

    /**
     * Method to add institute
     *
     * @param instituteDTO new institute
     * @return true if success
     */
    public boolean addInstitute(InstituteDTO instituteDTO);

    /**
     * Method to remove institute
     *
     * @param id instute id to remove
     * @return true if success
     */
    public boolean removeInstitute(Long id);

    /**
     * Method to edit institute
     *
     * @param instituteDTO new institute data
     * @return true if success
     */
    public boolean editInstitute(InstituteDTO instituteDTO);

    /**
     * Method to get departaments' chief id
     *
     * @param departamentId departament id
     * @return Long with chief id
     */
    public Long getDepartamentChief(Long departamentId);

    /**
     * Method to get departaments' rooms by departament id
     *
     * @param departamentId departament id
     * @return List of rooms
     */
    public List<RoomDTO> getDepartamentRoomsById(Long departamentId);
}
