package pl.polsl.reservations.roomManagement;

import pl.polsl.reservationsdatabasebeanremote.database.controllers.ReservationsFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomFacadeRemote;

import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 * Created by Krzysztof Stręk on 2016-05-09.
 */
@Stateful(mappedName = "RoomManagementFacade")
public class RoomManagementFacade implements RoomManagementFacadeRemote {

    @EJB
    ReservationsFacadeRemote reservation;

    @Override
    public String addEquipment(long roomId, long equipmentId) {
        return "testdDfs";
    }
}
