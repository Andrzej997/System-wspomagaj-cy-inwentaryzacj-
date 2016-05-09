package pl.polsl.reservations.roomManagement;

import javax.ejb.Remote;
import java.util.List;
import java.util.Map;

/**
 * Created by Krzysztof Stręk on 2016-05-09.
 */
@Remote
public interface RoomManagementFacadeRemote {

    String addEquipment(long roomId, long equipmentId);
    List<Map<String, String>> getRoomsList();
    List<Map<String, String>> getRoomEquipment(int number);
}
