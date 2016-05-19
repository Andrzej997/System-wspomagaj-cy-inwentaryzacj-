package pl.polsl.reservations.client;

import pl.polsl.reservations.dto.EquipmentDTO;
import pl.polsl.reservations.dto.ReservationDTO;
import pl.polsl.reservations.dto.RoomDTO;
import pl.polsl.reservations.roomManagement.RoomManagementFacade;
import pl.polsl.reservations.schedule.ScheduleFacade;

import java.util.List;

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

        ScheduleFacade schedule = (ScheduleFacade) l.getRemote("ScheduleFacade");

        RoomManagementFacade test2 = (RoomManagementFacade) l.getRemote("RoomManagementFacade");
        List<RoomDTO> s = test2.getRoomsList();

        int roomId = (int)s.get(0).getId();
        s = test2.getRoomsList();
//        test2.assignUserToRoom(roomId, 1);

        List<EquipmentDTO> beforeUpdate = test2.getRoomEquipment(roomId);

        test2.addEquipment(roomId, "Projektor", 4, (short)1, (short)1);

        List<EquipmentDTO> u1 = test2.getRoomEquipment(roomId);

        test2.removeEquipment((int)u1.get(0).getId());

        List<EquipmentDTO> u2 = test2.getRoomEquipment(roomId);

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

        List<ReservationDTO> reservationsByRoom = schedule.getRoomSchedule(1, 2016, true);
        List<ReservationDTO> reservationsByUser = schedule.getReservationsByUser(1);
        List<ReservationDTO> detailedReservationsByRoom = schedule.getDetailedRoomSchedule(1, 2016, 1, true);
        schedule.createReservation(1, 12, 18, 0, 2016, true, 1, 1);
        List<ReservationDTO> reservationsByRoomUpdated = schedule.getRoomSchedule(1, 2016, true);
    }
}
