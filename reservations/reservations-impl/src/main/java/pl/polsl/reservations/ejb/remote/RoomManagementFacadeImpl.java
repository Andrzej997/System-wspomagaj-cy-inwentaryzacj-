package pl.polsl.reservations.ejb.remote;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import pl.polsl.reservations.annotations.PrivilegeLevel;
import pl.polsl.reservations.builder.DTOBuilder;
import pl.polsl.reservations.dto.*;
import pl.polsl.reservations.ejb.dao.*;
import pl.polsl.reservations.entities.*;
import pl.polsl.reservations.interceptors.PrivilegeInterceptor;
import pl.polsl.reservations.logger.LoggerImpl;

/**
 * Created by Krzysztof Stręk on 2016-05-09.
 */
@Stateful(mappedName = "RoomManagementFacade")
@Interceptors({LoggerImpl.class, PrivilegeInterceptor.class})
public class RoomManagementFacadeImpl extends AbstractBusinessFacadeImpl implements RoomManagementFacade {

    @EJB
    RoomDao roomsDAO;

    @EJB
    UsersDao userDAO;

    @EJB
    DepartamentsDao departmentDAO;

    @EJB
    EquipmentStateDao equipmentStateDAO;

    @EJB
    EquipmentTypeDao equipmentTypeDAO;

    @EJB
    WorkersDao workersDAO;

    @EJB
    EquipmentDao equipmentDAO;

    public RoomManagementFacadeImpl() {
        super();
    }

    @Override
    @PrivilegeLevel(privilegeLevel = "P_EQUIPMENT_MANAGEMENT_WORKER")
    public void removeEquipmentType(int typeId) {
        EquipmentType type = equipmentTypeDAO.find(typeId);
        equipmentTypeDAO.remove(type);
    }

    @Override
    @PrivilegeLevel(privilegeLevel = "NONE")
    public void addEquipmentState(String definition) {
        EqupmentState state = new EqupmentState();
        state.setStateDefinition(definition);
        equipmentStateDAO.create(state);
    }

    @Override
    @PrivilegeLevel(privilegeLevel = "NONE")
    public void removeEquipmentState(int stateId) {
        EqupmentState state = equipmentStateDAO.find(stateId);
        equipmentStateDAO.remove(state);
    }

    @Override
    @PrivilegeLevel(privilegeLevel = "NONE")
    public void addEquipmentType(String shortDescription, String longDescription) {
        EquipmentType type = new EquipmentType();
        type.setShortDescription(shortDescription);
        type.setLongDescription(longDescription);
        equipmentTypeDAO.create(type);
    }

    @Override
    @PrivilegeLevel(privilegeLevel = "NONE")
    public void assignKeeperToRoom(int roomId, int workerId) {
        Room room = roomsDAO.find(roomId);
        room.setKeeper(workersDAO.find(workerId));
        roomsDAO.edit(room);
    }

    @Override
    @PrivilegeLevel(privilegeLevel = "NONE")
    public UserDTO getRoomKeeper(int roomId) {
        Room room = roomsDAO.find(roomId);
        Workers w = room.getKeeper();
        Users u = userDAO.find(w.getId());
        return DTOBuilder.buildUserDTO(u, w);
    }

    @Override
    @PrivilegeLevel(privilegeLevel = "NONE")
    public void addEquipment(int roomId, String name, int quantity, short stateId, short typeId) {
        Equipment newEquipment = new Equipment();
        newEquipment.setEquipmentName(name);
        newEquipment.setQuantity(quantity);
        newEquipment.setEquipmentState(equipmentStateDAO.find(stateId));
        newEquipment.setEquipmentType(equipmentTypeDAO.find(typeId));

        Room room = roomsDAO.find(roomId);

        newEquipment.setRoom(room);
        equipmentDAO.create(newEquipment);
    }

    @Override
    public List<EquipmentDTO> getDepartmentEquipment(int departmentId) {
        List<Room> departmentRooms = departmentDAO.getRoomCollectionById((long)departmentId);
        List<EquipmentDTO> result = new ArrayList<>();

        for (Room room : departmentRooms) {
            List<Equipment> roomEquipment = room.getEquipmentCollection();
            for (Equipment e : roomEquipment) {
                result.add(DTOBuilder.buildEquipmentDTO(e));
            }
        }

        return result;
    }

    @Override
    @PrivilegeLevel(privilegeLevel = "NONE")
    public List<RoomDTO> getRoomsList() {
        List<Room> rooms = roomsDAO.findAll();
        List<RoomDTO> result = new ArrayList<>();

        for (Room r : rooms) {
            result.add(DTOBuilder.buildRoomDTO(r));
        }

        return result;
    }

    @Override
    @PrivilegeLevel(privilegeLevel = "NONE")
    public List<RoomDTO> getRoomsWithNumberOfSeatsHigherEqualThan(Number numberOfSeats) {
        List<Room> rooms = roomsDAO.getRoomWithNumOfSeatsHigherOrEqualThan(numberOfSeats);
        List<RoomDTO> result = new ArrayList<>();

        for (Room r : rooms) {
            result.add(DTOBuilder.buildRoomDTO(r));
        }

        return result;
    }

    @Override
    @PrivilegeLevel(privilegeLevel = "NONE")
    public List<EquipmentDTO> getRoomEquipment(int roomId) {

        List<Equipment> equpment = equipmentDAO.getEquipmentByRoomNumber(roomsDAO.find(roomId).getRoomNumber());
        List<EquipmentDTO> result = new ArrayList<>();

        for (Equipment e : equpment) {
            result.add(DTOBuilder.buildEquipmentDTO(e));
        }

        return result;
    }

    @Override
    @PrivilegeLevel(privilegeLevel = "NONE")
    public void moveEquipment(int equipmentId, int roomToId) {
        Equipment e = equipmentDAO.find(equipmentId);
        e.setRoom(roomsDAO.find(roomToId));
        equipmentDAO.edit(e);
    }

    @Override
    @PrivilegeLevel(privilegeLevel = "NONE")
    public List<EquipmentStateDTO> getEquipmentStates() {
        List<EquipmentStateDTO> result = new ArrayList<>();
        for (EqupmentState es : equipmentStateDAO.findAll()) {
            result.add(DTOBuilder.buildEquipmentStateDTO(es));
        }
        return result;
    }

    @Override
    @PrivilegeLevel(privilegeLevel = "NONE")
    public List<EquipmentTypeDTO> getEquipmentTypes() {
        List<EquipmentTypeDTO> result = new ArrayList<>();
        for (EquipmentType et : equipmentTypeDAO.findAll()) {
            result.add(DTOBuilder.buildEquipmentTypeDTO(et));
        }
        return result;
    }

    @Override
    @PrivilegeLevel(privilegeLevel = "NONE")
    public void assignUserToRoom(int roomId, int workerId) {
        Room room = roomsDAO.find(roomId);

        Workers worker = workersDAO.find(workerId);

        worker.setRoom(room);

        workersDAO.edit(worker);
    }

    @Override
    @PrivilegeLevel(privilegeLevel = "NONE")
    public void removeEquipment(int equipmentId) {
        Equipment eq = equipmentDAO.find(equipmentId);
        equipmentDAO.remove(eq);
    }
    
    @Override
    public Boolean certificateBean(String certificate){
        Boolean certificateBean = super.certificateBean(certificate);
        roomsDAO.setUserContext(certificate);
        userDAO.setUserContext(certificate);
        departmentDAO.setUserContext(certificate);
        equipmentStateDAO.setUserContext(certificate);
        equipmentTypeDAO.setUserContext(certificate);
        workersDAO.setUserContext(certificate);
        equipmentDAO.setUserContext(certificate);
        return certificateBean;
    }
    
}
