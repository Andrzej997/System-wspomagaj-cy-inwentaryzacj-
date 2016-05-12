package pl.polsl.reservations.client;

import pl.polsl.reservations.roomManagement.RoomManagementFacadeRemote;
import pl.polsl.reservations.schedule.ScheduleFacadeRemote;
import pl.polsl.reservations.userManagement.UserManagementFacadeRemote;

import java.util.List;
import java.util.Map;

/**
 *
 * @author matis
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Lookup l = new Lookup();

        ScheduleFacadeRemote schedule = (ScheduleFacadeRemote) l.getRemote("ScheduleFacade");

        RoomManagementFacadeRemote test2 = (RoomManagementFacadeRemote) l.getRemote("RoomManagementFacade");
        List<Map<String, String>> s = test2.getRoomsList();

        int roomId = Integer.valueOf(s.get(0).get("id"));

//        test2.assignUserToRoom(roomId, 1);

//        List<Map<String, String>> beforeUpdate = test2.getRoomEquipment(roomId);

//        test2.addEquipment(roomId, "Projektor", 4, (short)1, (short)1);

        List<Map<String, String>> u1 = test2.getRoomEquipment(roomId);

        test2.removeEquipment(Integer.valueOf(u1.get(0).get("id")));

        List<Map<String, String>> u2 = test2.getRoomEquipment(roomId);

//        int roomId2 = Integer.valueOf(s.get(1).get("number"));
//
//        List<Map<String, String>> afterUpdate2 = test2.getRoomEquipment(roomId2);
//
//        test2.moveEquipment(Integer.valueOf(afterUpdate.get(1).get("id")), roomId2);
//
//        List<Map<String, String>> afterUpdate3 = test2.getRoomEquipment(roomId2);
//
//        List<Map<String, String>> afterUpdate4 = test2.getRoomEquipment(roomId);

//        Map<String, String> result = test2.getRoomKeeper(roomId);
//
//        test2.assignKeeperToRoom(roomId, 3);
//
//        Map<String, String> resultAfter = test2.getRoomKeeper(roomId);

        schedule.getRoomSchedule(roomId);
    }
}
