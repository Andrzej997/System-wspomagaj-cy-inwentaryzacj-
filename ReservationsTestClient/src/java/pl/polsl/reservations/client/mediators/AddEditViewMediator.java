/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.reservations.client.mediators;

import java.util.List;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.AddEditView;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.dto.ReservationDTO;
import pl.polsl.reservations.dto.RoomDTO;
import pl.polsl.reservations.roomManagement.RoomManagementFacade;
import pl.polsl.reservations.schedule.ScheduleFacade;

/**
 *
 * @author Pawe³
 */
public class AddEditViewMediator {

    private final ScheduleFacade scheduleFacade;
    private final RoomManagementFacade roomManagementFacade;
    private AddEditView addEditWindow;
    
    public AddEditViewMediator() {
        scheduleFacade = (ScheduleFacade) Lookup.getRemote("ScheduleFacade");
        roomManagementFacade = (RoomManagementFacade) Lookup.getRemote("RoomManagementFacade");
        
    }
    
    public AddEditView createView(MainView parent) {
        addEditWindow = new AddEditView(parent, this);
        getRooms();
        return addEditWindow;
    }
    
    public void getRooms() {
        List<RoomDTO> roomsList = roomManagementFacade.getRoomsList();
        roomsList.stream().forEach((room) -> {
            addEditWindow.getRoomCb().addItem(room.getNumber());
        });    
    }
    
}
