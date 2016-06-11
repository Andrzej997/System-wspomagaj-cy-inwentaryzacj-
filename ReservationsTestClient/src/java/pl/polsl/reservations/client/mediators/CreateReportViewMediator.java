package pl.polsl.reservations.client.mediators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.CreateRaportView;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.client.views.utils.AddTypeEnum;
import pl.polsl.reservations.client.views.utils.RoomComboBox;
import pl.polsl.reservations.dto.DepartamentDTO;
import pl.polsl.reservations.dto.EquipmentDTO;
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

    private final RoomManagementFacade roomManagementFacade = Lookup.getRoomManagementFacade();
    private final UserManagementFacade userManagementFacade = Lookup.getUserManagementFacade();
    private final UserFacade userFacade = Lookup.getUserFacade();

    public CreateReportViewMediator() {
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
            case DEVICE_EDIT:
                setDeviceDataToEdit();
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
        roomComboBox.selectItem(1, 1);
    }

    private void setEditedRooms() {
        List<RoomDTO> roomsList = roomManagementFacade.getRoomsList();
        HashMap<Integer, List<Integer>> numbersMap = new HashMap<>();
        RoomComboBox roomComboBox = (RoomComboBox) createRaportView.getEditedRoomCb();
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
        roomComboBox.selectItem(1, 1);
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

    private void setDeviceDataToEdit() {
        setEditedRooms();
        setRooms();
        getRoomEquipment();
    }

    private void getRoomEquipment() {
        RoomDTO room = roomManagementFacade.getRoom(createRaportView.getEditedRoomCb().getSelectedItem());
        List<EquipmentDTO> roomEquipment = roomManagementFacade.getRoomEquipment(room.getId().intValue());
        if (roomEquipment != null && !roomEquipment.isEmpty()) {
            createRaportView.getDeviceCb().removeAllItems();
            for (EquipmentDTO equipment : roomEquipment) {
                createRaportView.getDeviceCb().addItem(equipment.getName());
            }
            createRaportView.getDeviceCb().setSelectedIndex(0);
            List<EquipmentTypeDTO> equipmentTypes = roomManagementFacade.getEquipmentTypes();
            for (EquipmentTypeDTO equipmentType : equipmentTypes) {
                createRaportView.getTypeCb().addItem(equipmentType.getDescription());
            }
            EquipmentDTO equipment = roomEquipment.get(0);
            if (equipmentTypes != null && !equipmentTypes.isEmpty()) {
                createRaportView.getTypeCb().setSelectedItem(equipment.getType());
            }
            createRaportView.getNumberTf().setText(equipment.getQuantity().toString());
            createRaportView.getNameTf().setText(equipment.getName());
            List<EquipmentStateDTO> equipmentStates = roomManagementFacade.getEquipmentStates();
            for (EquipmentStateDTO equipmentStateDTO : equipmentStates) {
                createRaportView.getStateCb().addItem(equipmentStateDTO.getDescription());
            }
            createRaportView.getStateCb().setSelectedItem(equipment.getState());
        }

    }

    public Boolean onAddState() {
        String newState = createRaportView.getNameTf().getText();
        return roomManagementFacade.addEquipmentState(newState);
    }

    public Boolean onAddType() {
        String shortDescription = createRaportView.getNameTf().getText();
        String longDescription = createRaportView.getDescriptionTf().getText();
        return roomManagementFacade.addEquipmentType(shortDescription, longDescription);
    }

    public void onRoomChange() {
        Integer selectedItem = createRaportView.getEditedRoomCb().getSelectedItem();
        if (selectedItem != null) {
            RoomDTO room = roomManagementFacade.getRoom(selectedItem);
            List<EquipmentDTO> roomEquipment = roomManagementFacade.getRoomEquipment(room.getId().intValue());
            createRaportView.getDeviceCb().removeAllItems();
            if (roomEquipment != null && !roomEquipment.isEmpty()) {
                for (EquipmentDTO equipment : roomEquipment) {
                    createRaportView.getDeviceCb().addItem(equipment.getName());
                }
                createRaportView.getDeviceCb().setSelectedIndex(0);
                EquipmentDTO equipment = roomEquipment.get(0);
                createRaportView.getTypeCb().setSelectedItem(equipment.getType());
                createRaportView.getNumberTf().setText(equipment.getQuantity().toString());
                createRaportView.getNameTf().setText(equipment.getName());
                createRaportView.getStateCb().setSelectedItem(equipment.getState());
            }
        }
    }

    public void onEquipmentSelectionChange() {
        Integer selectedItem = createRaportView.getEditedRoomCb().getSelectedItem();
        RoomDTO room = roomManagementFacade.getRoom(selectedItem);
        List<EquipmentDTO> roomEquipment = roomManagementFacade.getRoomEquipment(room.getId().intValue());
        int selectedIndex = createRaportView.getDeviceCb().getSelectedIndex();
        EquipmentDTO equipment = null;
        try {
            equipment = roomEquipment.get(selectedIndex);
        } catch (ArrayIndexOutOfBoundsException ex) {

        }
        if (equipment != null) {
            createRaportView.getTypeCb().setSelectedItem(equipment.getType());
            createRaportView.getNumberTf().setText(equipment.getQuantity().toString());
            createRaportView.getNameTf().setText(equipment.getName());
            createRaportView.getStateCb().setSelectedItem(equipment.getState());
        }
    }

    public void onEditAction() {
        Integer selectedRoomIndex = createRaportView.getEditedRoomCb().getSelectedItem();
        RoomDTO selectedRoom = roomManagementFacade.getRoom(selectedRoomIndex);
        Integer targetRoomIndex = createRaportView.getRoomCb().getSelectedItem();
        RoomDTO targetRoom = roomManagementFacade.getRoom(targetRoomIndex);
        int selectedDeviceIndex = createRaportView.getDeviceCb().getSelectedIndex();
        List<EquipmentDTO> roomEquipment = roomManagementFacade.getRoomEquipment(selectedRoom.getId().intValue());
        EquipmentDTO oldEqiupment = roomEquipment.get(selectedDeviceIndex);
        int selectedTypeIndex = createRaportView.getTypeCb().getSelectedIndex();
        List<EquipmentTypeDTO> equipmentTypes = roomManagementFacade.getEquipmentTypes();
        EquipmentTypeDTO equipmentType = equipmentTypes.get(selectedTypeIndex);
        int selectedStateIndex = createRaportView.getStateCb().getSelectedIndex();
        List<EquipmentStateDTO> equipmentStates = roomManagementFacade.getEquipmentStates();
        EquipmentStateDTO equipmentState = equipmentStates.get(selectedStateIndex);
        String equipmentName = createRaportView.getNameTf().getText();
        Integer equipmentQuantity = Integer.parseInt(createRaportView.getNumberTf().getText());
        if (!Objects.equals(targetRoomIndex, selectedRoomIndex)) {
            roomManagementFacade.editEquipment(oldEqiupment.getId(), equipmentName, equipmentQuantity,
                    equipmentState.getId().shortValue(), equipmentType.getId().shortValue());
            try {
                roomManagementFacade.moveEquipment(oldEqiupment.getId().intValue(), targetRoom.getId().intValue());
            } catch (UnauthorizedAccessException ex) {
                Logger.getLogger(CreateReportViewMediator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            roomManagementFacade.editEquipment(oldEqiupment.getId(), equipmentName, equipmentQuantity,
                    equipmentState.getId().shortValue(), equipmentType.getId().shortValue());
        }
    }

    public void onDeleteAction() {
        Integer selectedRoomIndex = createRaportView.getEditedRoomCb().getSelectedItem();
        RoomDTO selectedRoom = roomManagementFacade.getRoom(selectedRoomIndex);
        int selectedDeviceIndex = createRaportView.getDeviceCb().getSelectedIndex();
        List<EquipmentDTO> roomEquipment = roomManagementFacade.getRoomEquipment(selectedRoom.getId().intValue());
        EquipmentDTO oldEqiupment = roomEquipment.get(selectedDeviceIndex);
        try {
            roomManagementFacade.removeEquipment(oldEqiupment.getId().intValue());
        } catch (UnauthorizedAccessException ex) {
            Logger.getLogger(CreateReportViewMediator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
