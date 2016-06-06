package pl.polsl.reservations.client.mediators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.CreateRaportView;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.client.views.utils.AddTypeEnum;
import pl.polsl.reservations.client.views.utils.RoomComboBox;
import pl.polsl.reservations.dto.DepartamentDTO;
import pl.polsl.reservations.dto.EquipmentStateDTO;
import pl.polsl.reservations.dto.EquipmentTypeDTO;
import pl.polsl.reservations.dto.RoomDTO;
import pl.polsl.reservations.dto.RoomTypesDTO;
import pl.polsl.reservations.dto.UnauthorizedAccessException;
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
        List<RoomTypesDTO> roomTypes = roomManagementFacade.getRoomTypes();
        for (RoomTypesDTO roomType : roomTypes) {
            createRaportView.getTypeCb().addItem(roomType.getShortDescription());
        }
    }

    public Boolean onAddRoom() {
        String roomNumber = createRaportView.getRoomIdTf().getText();
        String numberOfSeats = createRaportView.getNumberTf().getText();
        String departament = (String) createRaportView.getDepartmentCb().getSelectedItem();
        Integer keeperIndex = createRaportView.getKeeperCb().getSelectedIndex();
        List<UserDTO> usersList = userFacade.getUsersWithLowerPrivilegeLevel();
        List<UserDTO> keepersList = new ArrayList<>();
        for (UserDTO user : usersList) {
            if (user.getPrivilegeLevel() < 6) {
                keepersList.add(user);
            }
        }
        Integer roomNumberInt = Integer.parseInt(roomNumber);
        Integer numberOfSeatsInt = Integer.parseInt(numberOfSeats);
        List<DepartamentDTO> allDepartaments = userManagementFacade.getAllDepartaments();
        Long departamentId = 0l;
        for (DepartamentDTO departamentDTO : allDepartaments) {
            if (departamentDTO.getName().equals(departament)) {
                departamentId = departamentDTO.getId();
            }
        }
        UserDTO keeper = keepersList.get(keeperIndex);
        List<RoomTypesDTO> roomTypes = roomManagementFacade.getRoomTypes();
        RoomTypesDTO roomType = roomTypes.get(createRaportView.getTypeCb().getSelectedIndex());
        return roomManagementFacade.addRoom(roomNumberInt, keeper.getId(), departamentId, roomType.getId().longValue(), numberOfSeatsInt);
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
        setRooms();
    }

    private void setRooms() {
        List<RoomDTO> roomsList = roomManagementFacade.getRoomsList();
        HashMap<Integer, List<Integer>> numbersMap = new HashMap<>();
        RoomComboBox roomComboBox = (RoomComboBox) createRaportView.getRoomCb();
        List<Integer> floors = new ArrayList<>();
        for (RoomDTO room : roomsList) {
            Integer roomNumber = room.getNumber();
            Integer floor = roomNumber / 100;
            Integer number = roomNumber % 100;
            if (numbersMap.containsKey(floor)) {
                List<Integer> numbers = numbersMap.get(floor);
                numbers.add(number);
                numbersMap.put(floor, numbers);
            } else {
                List<Integer> numbers = new ArrayList<>();
                numbers.add(number);
                numbersMap.put(floor, numbers);
                floors.add(floor);
            }
        }
        roomComboBox.setFloors(floors);
        for (Map.Entry<Integer, List<Integer>> floorEntry : numbersMap.entrySet()) {
            roomComboBox.setRooms(floorEntry.getValue(), floorEntry.getKey());
        }
        roomComboBox.selectItem(1,1);
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
        Integer roomNumber = createRaportView.getRoomCb().getSelectedItem();
        RoomDTO room = roomManagementFacade.getRoom(roomNumber);
        try {
            roomManagementFacade.addEquipment(room.getId().intValue(), equipmentName, equipmentQuantity, selectedState.getId().shortValue(), selectedType.getId().shortValue());
        } catch (UnauthorizedAccessException ex) {

        }
    }

    public Boolean onAddState() {
        String newState = createRaportView.getNameTf().getText();
        return roomManagementFacade.addEquipmentState(newState);
    }

    public Boolean onAddType() {

        //return roomManagementFacade.addEquipmentType(shortDescription, longDescription);
        return true;
    }

}
