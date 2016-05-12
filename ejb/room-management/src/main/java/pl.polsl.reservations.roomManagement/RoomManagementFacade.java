package pl.polsl.reservations.roomManagement;

import pl.polsl.reservationsdatabasebeanremote.database.*;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.*;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Krzysztof Stręk on 2016-05-09.
 */
@Stateful(mappedName = "RoomManagementFacade")
public class RoomManagementFacade implements RoomManagementFacadeRemote {

    @EJB
    RoomFacadeRemote roomsDAO;

    @EJB
    UsersFacadeRemote userDAO;

    @EJB
    DepartamentsFacadeRemote departmentDAO;

    @EJB
    EquipmentStateFacadeRemote equipmentStateDAO;

    @EJB
    EquipmentTypeFacadeRemote equipmentTypeDAO;

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
    EquipmentFacadeRemote equipmentDAO;

    @Override
    public void addEquipmentType(String shortDescription, String longDescription) {
        EquipmentType type = new EquipmentType();
        type.setShortDescription(shortDescription);
        type.setLongDescription(longDescription);
        equipmentTypeDAO.create(type);
    }

    @EJB
    WorkersFacadeRemote workersDAO;

    public RoomManagementFacade() {
    }

    @Override
    public void assignKeeperToRoom(int roomId, int workerId) {
        Room room = roomsDAO.find(roomId);
        room.setKeeperId(workersDAO.find(workerId));
        roomsDAO.edit(room);
    }

    @Override
    public Map<String, String> getRoomKeeper(int roomId) {
        Room room = roomsDAO.find(roomId);
        Workers w = room.getKeeperId();

        Map<String, String> result = new HashMap<>();
        result.put("id", String.valueOf(w.getId()));
        result.put("address", w.getAdress());
        result.put("department", w.getDepartamentId().getDepratamentName());
        result.put("grade", w.getGrade());
        result.put("pesel", w.getPesel());
        result.put("name", w.getWorkerName());
        result.put("surname", w.getSurname());

        return result;
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
    public List<Map<String, String>> getRoomsList() {
        List<Room> rooms = roomsDAO.findAll();
        List<Map<String, String>> result = new ArrayList<>();

        for(Room r: rooms) {
            Map<String, String> roomData = new HashMap<>();

            roomData.put("id", String.valueOf(r.getId()));
            roomData.put("number", String.valueOf(r.getRoomNumber()));
            roomData.put("department", r.getDepartamentId().getDepratamentName());
            Workers w = r.getKeeperId();
            roomData.put("keeper", w.getWorkerName() + " " + w.getSurname());
            roomData.put("type", r.getRoomType().getShortDescription());

            result.add(roomData);
        }

        return result;
    }

    @Override
    public List<Map<String, String>> getRoomEquipment(int roomId) {

        List<Equipment> equpment = equipmentDAO.getEquipmentByRoomNumber(roomsDAO.find(roomId).getRoomNumber());
        List<Map<String, String>> result = new ArrayList<>();

        for(Equipment e: equpment) {
            Map<String, String> roomData = new HashMap<>();

            roomData.put("id", String.valueOf(e.getId()));
            roomData.put("name", e.getEquipmentName());
            roomData.put("quantity", String.valueOf(e.getQuantity()));
            roomData.put("state", e.getEquipmentState().getStateDefinition());
            roomData.put("type", e.getEquipmentType().getShortDescription());

            result.add(roomData);
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
    public List<Map<String, String>> getEquipmentStates() {
        List<Map<String, String>> result = new ArrayList<>();
        for(EqupmentState es: equipmentStateDAO.findAll()) {
            Map<String, String> type = new HashMap<>();
            type.put("id", String.valueOf(es.getStateId()));
            type.put("description", es.getStateDefinition());
        }
        return result;
    }

    @Override
    public List<Map<String, String>> getEquipmentTypes() {
        List<Map<String, String>> result = new ArrayList<>();
        for(EquipmentType et: equipmentTypeDAO.findAll()) {
            Map<String, String> type = new HashMap<>();
            type.put("id", String.valueOf(et.getId()));
            type.put("description", et.getShortDescription());
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
        equipmentDAO.remove(equipmentDAO.find(equipmentId));
    }
}
