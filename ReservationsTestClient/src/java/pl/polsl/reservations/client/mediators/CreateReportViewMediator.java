package pl.polsl.reservations.client.mediators;

import java.util.ArrayList;
import java.util.List;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.CreateRaportView;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.client.views.utils.AddTypeEnum;
import pl.polsl.reservations.dto.DepartamentDTO;
import pl.polsl.reservations.dto.EquipmentStateDTO;
import pl.polsl.reservations.dto.EquipmentTypeDTO;
import pl.polsl.reservations.dto.RoomDTO;
import pl.polsl.reservations.dto.UserDTO;
import pl.polsl.reservations.ejb.remote.RoomManagementFacade;
import pl.polsl.reservations.ejb.remote.UserFacade;
import pl.polsl.reservations.ejb.remote.UserManagementFacade;

/**
 *
 * @author matis
 */
public class CreateReportViewMediator {

    private CreateRaportView createRaportView;

    private final RoomManagementFacade roomManagementFacade;
    private final UserManagementFacade userManagementFacade;
    private final UserFacade userFacade;

    public CreateReportViewMediator() {
        roomManagementFacade = (RoomManagementFacade) Lookup.getRemote("RoomManagementFacade");
        userManagementFacade = (UserManagementFacade) Lookup.getRemote("UserManagementFacade");
        userFacade = (UserFacade) Lookup.getRemote("UserFacade");
    }

    public CreateRaportView createView(MainView view, AddTypeEnum option) {
        createRaportView = new CreateRaportView(view, option, this);
        switch (option) {
            case ROOM:
                setAddRoomData();
                break;
            case DEVICE:
                setDeviceData();
                break;
            case STATE:
                break;
            case TYPE:
                break;
        }

        return createRaportView;
    }

    private void setAddRoomData() {
        List<DepartamentDTO> departamentsList = userManagementFacade.getAllDepartaments();
        for (DepartamentDTO departament : departamentsList) {
            createRaportView.getDepartmentCb().addItem(departament.getName());
        }
        List<UserDTO> usersList = userFacade.getUsersWithLowerPrivilegeLevel();
        for (UserDTO user : usersList) {
            if (user.getPrivilegeLevel() < 6) {
                String userData = user.getName() + " " + user.getSurname();
                createRaportView.getKeeperCb().addItem(userData);
            }
        }
    }

    public void onAddRoom() {
        String roomNumber = createRaportView.getRoomIdTf().getText();
        String numberOfSeats = createRaportView.getNumberTf().getText();
        String departament = (String) createRaportView.getDepartmentCb().getSelectedItem();
        String keeperData = (String) createRaportView.getKeeperCb().getSelectedItem();
        Integer keeperIndex = createRaportView.getKeeperCb().getSelectedIndex();
        List<UserDTO> usersList = userFacade.getUsersWithLowerPrivilegeLevel();
        List<UserDTO> keepersList = new ArrayList<>();
        for (UserDTO user : usersList) {
            if (user.getPrivilegeLevel() < 6) {
                keepersList.add(user);
            }
        }
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setDepartment(departament);
        roomDTO.setNumber(Integer.parseInt(roomNumber));
        roomDTO.setType("TODO");
        UserDTO keeper = keepersList.get(keeperIndex);
        roomDTO.setKeeper(keeper.getId().toString());
    }

    private void setDeviceData() {
        List<EquipmentStateDTO> equipmentStates = roomManagementFacade.getEquipmentStates();
        List<EquipmentTypeDTO> equipmentTypes = roomManagementFacade.getEquipmentTypes();
        for (EquipmentStateDTO equipmentState : equipmentStates) {
            createRaportView.getStateCb().addItem(equipmentState.getDescription());
        }
        for (EquipmentTypeDTO equipmentType : equipmentTypes) {
            createRaportView.getTypeCb().addItem(equipmentType.getDescription());
        }
    }

    public void onAddDevice() {
        List<EquipmentStateDTO> equipmentStates = roomManagementFacade.getEquipmentStates();
        List<EquipmentTypeDTO> equipmentTypes = roomManagementFacade.getEquipmentTypes();
        Integer selectedTypeIndex = createRaportView.getTypeCb().getSelectedIndex();
        Integer selectedStateIndex = createRaportView.getStateCb().getSelectedIndex();
        EquipmentStateDTO selectedState = equipmentStates.get(selectedStateIndex);
        EquipmentTypeDTO selectedType = equipmentTypes.get(selectedTypeIndex);
        String equipmentQuantityString = createRaportView.getNumberTf().getText();
        String equipmentName = createRaportView.getNameTf().getText();
        Integer equipmentQuantity = Integer.parseInt(equipmentQuantityString);
        //roomManagementFacade.addEquipment(0, equipmentName, 0, 0, 0);
    }
    
    public Boolean onAddState(){
        String newState = createRaportView.getNameTf().getText();
        return roomManagementFacade.addEquipmentState(newState);
    }
    
    public Boolean onAddType(){
        
        //return roomManagementFacade.addEquipmentType(shortDescription, longDescription);
        return true;
    }

}
