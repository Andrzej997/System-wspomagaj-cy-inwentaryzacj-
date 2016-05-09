package pl.polsl.reservations.roomManagement;

import javax.ejb.Remote;

/**
 * Created by Krzysztof Stręk on 2016-05-09.
 */
@Remote
public interface RoomManagementFacadeRemote {
    String addEquipment(long roomId, long equipmentId);
}
