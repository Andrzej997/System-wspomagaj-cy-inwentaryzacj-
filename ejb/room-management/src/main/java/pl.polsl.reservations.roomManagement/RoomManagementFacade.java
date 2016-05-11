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

    @EJB
    EquipmentFacadeRemote equipmentDAO;

    @Override
    public void addEquipment(int roomNumber, String name, int quantity, short stateId, short typeId) {
        Equipment newEquipment = new Equipment();
        newEquipment.setEquipmentName(name);
        newEquipment.setQuantity(quantity);
        newEquipment.setEquipmentState(equipmentStateDAO.find(Short.valueOf(stateId)));
        newEquipment.setEquipmentType(equipmentTypeDAO.find(Short.valueOf(typeId)));

        Room room = roomsDAO.getRoomByNumber(roomNumber);

        newEquipment.setRoomId(room);
        equipmentDAO.create(newEquipment);

        room.getEquipmentCollection().add(newEquipment);
        roomsDAO.edit(room);
    }

    @Override
    public List<Map<String, String>> getRoomsList() {
        List<Room> rooms = roomsDAO.findAll();
        List<Map<String, String>> result = new ArrayList<>();

        for(Room r: rooms) {
            Map<String, String> roomData = new HashMap<>();

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
    public List<Map<String, String>> getRoomEquipment(int number) {
        List<Equipment> equpment = roomsDAO.getRoomByNumber(number).getEquipmentCollection();
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
}
