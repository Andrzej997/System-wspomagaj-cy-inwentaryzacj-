package pl.polsl.reservations.roomManagement;

import pl.polsl.reservationsdatabasebeanremote.database.Equipment;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.Workers;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.DepartamentsFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.UsersFacadeRemote;

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

    @Override
    public String addEquipment(long roomId, long equipmentId) {
        return "testdDfs";
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
}
