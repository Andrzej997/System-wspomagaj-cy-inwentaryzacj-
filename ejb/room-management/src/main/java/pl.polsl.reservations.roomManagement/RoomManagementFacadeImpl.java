package pl.polsl.reservations.roomManagement;

import pl.polsl.reservations.dto.*;
import pl.polsl.reservationsdatabasebeanremote.database.*;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.*;
import pl.polsl.reservationsdatabasebeanremote.database.logger.LoggerImpl;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-05-09.
 */
@Stateful(mappedName = "RoomManagementFacade")
@Interceptors({LoggerImpl.class})
public class RoomManagementFacadeImpl implements RoomManagementFacade {

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

    @Override
    public void removeEquipmentType(int typeId) {
        EquipmentType type = equipmentTypeDAO.find(typeId);
        equipmentTypeDAO.remove(type);
    }

    @Override
    public void addEquipmentState(String definition) {
        EqupmentState state = new EqupmentState();
        state.setStateDefinition(definition);
        equipmentStateDAO.create(state);
    }

    @Override
    public void removeEquipmentState(int stateId) {
        EqupmentState state = equipmentStateDAO.find(stateId);
        equipmentStateDAO.remove(state);
    }

    @EJB
    EquipmentDao equipmentDAO;

    @Override
    public void addEquipmentType(String shortDescription, String longDescription) {
        EquipmentType type = new EquipmentType();
        type.setShortDescription(shortDescription);
        type.setLongDescription(longDescription);
        equipmentTypeDAO.create(type);
    }

    @EJB
    WorkersDao workersDAO;

    public RoomManagementFacadeImpl() {
    }

    @Override
    public void assignKeeperToRoom(int roomId, int workerId) {
        Room room = roomsDAO.find(roomId);
        room.setKeeperId(workersDAO.find(workerId));
        roomsDAO.edit(room);
    }

    @Override
    public UserDTO getRoomKeeper(int roomId) {
        Room room = roomsDAO.find(roomId);
        Workers w = room.getKeeperId();

        return new UserDTO(w);
    }

    @Override
    public void addEquipment(int roomId, String name, int quantity, short stateId, short typeId) {
        Equipment newEquipment = new Equipment();
        newEquipment.setEquipmentName(name);
        newEquipment.setQuantity(quantity);
        newEquipment.setEquipmentState(equipmentStateDAO.find(stateId));
        newEquipment.setEquipmentType(equipmentTypeDAO.find(typeId));

        Room room = roomsDAO.find(roomId);

        newEquipment.setRoomId(room);
        equipmentDAO.create(newEquipment);
    }

    @Override
    public List<RoomDTO> getRoomsList() {
        List<Room> rooms = roomsDAO.findAll();
        List<RoomDTO> result = new ArrayList<>();

        for(Room r: rooms) {
            result.add(new RoomDTO(r));
        }

        return result;
    }

    @Override
    public List<EquipmentDTO> getRoomEquipment(int roomId) {

        List<Equipment> equpment = equipmentDAO.getEquipmentByRoomNumber(roomsDAO.find(roomId).getRoomNumber());
//        List<Equipment> equpment = roomsDAO.find(roomId).getEquipmentCollection();
        List<EquipmentDTO> result = new ArrayList<>();

        for(Equipment e: equpment) {
            result.add(new EquipmentDTO(e));
        }

        return result;
    }

    @Override
    public void moveEquipment(int equipmentId, int roomToId) {
        Equipment e = equipmentDAO.find(equipmentId);
        e.setRoomId(roomsDAO.find(roomToId));
        equipmentDAO.edit(e);
    }

    @Override
    public List<EquipmentStateDTO> getEquipmentStates() {
        List<EquipmentStateDTO> result = new ArrayList<>();
        for(EqupmentState es: equipmentStateDAO.findAll()) {
            result.add(new EquipmentStateDTO(es));
        }
        return result;
    }

    @Override
    public List<EquipmentTypeDTO> getEquipmentTypes() {
        List<EquipmentTypeDTO> result = new ArrayList<>();
        for(EquipmentType et: equipmentTypeDAO.findAll()) {
            result.add(new EquipmentTypeDTO(et));
        }
        return result;
    }

    @Override
    public void assignUserToRoom(int roomId, int workerId) {
        Room room = roomsDAO.find(roomId);

        Workers worker = workersDAO.find(workerId);

        worker.setRoom(room);

        workersDAO.edit(worker);
    }

    @Override
    public void removeEquipment(int equipmentId) {
        Equipment eq = equipmentDAO.find(equipmentId);
        equipmentDAO.remove(eq);
    }
}
