package pl.polsl.reservations.client.mediators;

import java.util.List;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.AddEditRoomTypeView;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.client.views.utils.ButtonStyle;
import pl.polsl.reservations.dto.RoomTypesDTO;
import pl.polsl.reservations.ejb.remote.RoomManagementFacade;

/**
 *
 * @author matis
 */
public class AddEditRoomTypeViewMediator {

    private AddEditRoomTypeView addEditRoomTypeView;
    private final RoomManagementFacade roomManagementFacade = Lookup.getRoomManagementFacade();

    public AddEditRoomTypeViewMediator() {

    }

    public AddEditRoomTypeView createView(MainView window) {
        addEditRoomTypeView = new AddEditRoomTypeView(window, this);
        getRoomTypes();
        return addEditRoomTypeView;
    }

    private void getRoomTypes() {
        List<RoomTypesDTO> roomTypes = roomManagementFacade.getRoomTypes();
        addEditRoomTypeView.getRoomTypes().addItem("Create new");
        for (RoomTypesDTO roomType : roomTypes) {
            addEditRoomTypeView.getRoomTypes().addItem(roomType.getShortDescription());
        }
        addEditRoomTypeView.getRoomTypes().setSelectedItem("Create new");
    }

    public void onSelectionChange() {
        List<RoomTypesDTO> roomTypes = roomManagementFacade.getRoomTypes();
        String selectedItem = (String) addEditRoomTypeView.getRoomTypes().getSelectedItem();
        if (selectedItem.equals("Create new")) {
            addEditRoomTypeView.getRoomTypeField().setText("");
            addEditRoomTypeView.getRoomTypeDescField().setText("");
            ButtonStyle.setStyle(addEditRoomTypeView.getAddButton(), addEditRoomTypeView.getAddImg());
        } else {
            for (RoomTypesDTO roomType : roomTypes) {
                if (roomType.getShortDescription().equals(selectedItem)) {
                    addEditRoomTypeView.getRoomTypeField().setText(roomType.getShortDescription());
                    addEditRoomTypeView.getRoomTypeDescField().setText(roomType.getLongDescription());
                    ButtonStyle.setStyle(addEditRoomTypeView.getAddButton(), addEditRoomTypeView.getEditImg());
                    return;
                }
            }
        }
    }

    public void onAddEdit() {
        List<RoomTypesDTO> roomTypes = roomManagementFacade.getRoomTypes();
        String selectedItem = (String) addEditRoomTypeView.getRoomTypes().getSelectedItem();
        RoomTypesDTO newRoomTypeDTO = new RoomTypesDTO();
        if (selectedItem.equals("Create new")) {
            newRoomTypeDTO.setShortDescription(addEditRoomTypeView.getRoomTypeField().getText());
            newRoomTypeDTO.setShortDescription(addEditRoomTypeView.getRoomTypeDescField().getText());
            roomManagementFacade.addRoomType(newRoomTypeDTO);
        } else {
            for (RoomTypesDTO roomType : roomTypes) {
                if (roomType.getShortDescription().equals(selectedItem)) {
                    String shortDesc = addEditRoomTypeView.getRoomTypeField().getText();
                    String longDesc = addEditRoomTypeView.getRoomTypeDescField().getText();
                    if (roomType.getShortDescription().equals(shortDesc)
                            && roomType.getLongDescription().equals(longDesc)) {
                        return;
                    } else {
                        newRoomTypeDTO.setShortDescription(addEditRoomTypeView.getRoomTypeField().getText());
                        newRoomTypeDTO.setShortDescription(addEditRoomTypeView.getRoomTypeDescField().getText());
                        newRoomTypeDTO.setId(roomType.getId());
                        roomManagementFacade.editRoomType(newRoomTypeDTO);
                        return;
                    }
                }
            }
        }
    }

    public void onDelete() {
        List<RoomTypesDTO> roomTypes = roomManagementFacade.getRoomTypes();
        String selectedItem = (String) addEditRoomTypeView.getRoomTypes().getSelectedItem();
        if (selectedItem.equals("Create new")) {
            return;
        }
        for (RoomTypesDTO roomType : roomTypes) {
            if (roomType.getShortDescription().equals(selectedItem)) {
                roomManagementFacade.removeRoomType(roomType);
                return;
            }
        }
    }

}
