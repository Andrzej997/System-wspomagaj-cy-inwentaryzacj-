package pl.polsl.reservations.client.mediators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pl.polsl.reservations.client.ClientContext;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.AddEditUserView;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.client.views.utils.AddUserEnum;
import pl.polsl.reservations.client.views.utils.RoomComboBox;
import pl.polsl.reservations.dto.DepartamentDTO;
import pl.polsl.reservations.dto.PrivilegeLevelDTO;
import pl.polsl.reservations.dto.RoomDTO;
import pl.polsl.reservations.dto.UserDTO;
import pl.polsl.reservations.ejb.remote.RoomManagementFacade;
import pl.polsl.reservations.ejb.remote.UserFacade;
import pl.polsl.reservations.ejb.remote.UserManagementFacade;

/**
 *
 * @author matis
 */
public class AddEditUserViewMediator {

    private AddEditUserView addEditUserView;

    private final UserFacade userFacade;
    private final UserManagementFacade userManagementFacade;
    private final RoomManagementFacade roomManagementFacade;

    public AddEditUserViewMediator() {
        userFacade = (UserFacade) Lookup.getRemote("UserFacade");
        userManagementFacade = (UserManagementFacade) Lookup.getRemote("UserManagementFacade");
        roomManagementFacade = (RoomManagementFacade) Lookup.getRemote("RoomManagementFacade");
    }

    public AddEditUserView createView(MainView view, AddUserEnum option) {
        addEditUserView = new AddEditUserView(view, option, this);
        setDepartaments();
        if (option == AddUserEnum.EDIT) {
            getSelectedUserData();
        } else {
            getRooms();
            setPrivilegeLevels();
        }
        return addEditUserView;
    }

    public void setDepartaments() {
        List<DepartamentDTO> departamentsCollection = userManagementFacade.getAllDepartaments();
        for (DepartamentDTO departamentDTO : departamentsCollection) {
            addEditUserView.getDepartmentCb().addItem(departamentDTO.getName());
        }
    }

    public void getRooms() {
        List<RoomDTO> roomsList = roomManagementFacade.getRoomsList();
        HashMap<Integer, List<Integer>> numbersMap = new HashMap<>();
        RoomComboBox roomComboBox = (RoomComboBox) addEditUserView.getRoomCb();
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
    
    public void setPrivilegeLevels() {
        List<PrivilegeLevelDTO> allPrivilegeLevels = userManagementFacade.getAllPrivilegeLevels();
        for (PrivilegeLevelDTO privilegeLevel : allPrivilegeLevels) {
            addEditUserView.getPermissionCb().addItem(privilegeLevel.getDescription());
        }
        addEditUserView.getPermissionCb().setSelectedIndex(5);
    }

    private Long getSelectedPrivilegeLevel() {
        List<PrivilegeLevelDTO> allPrivilegeLevels = userManagementFacade.getAllPrivilegeLevels();
        int selectedIndex = addEditUserView.getPermissionCb().getSelectedIndex();
        return allPrivilegeLevels.get(selectedIndex).getPrivilegeLevel();
    }

    public Boolean onAddUser() {
        UserDTO newUser = new UserDTO();
        newUser.setAddress(addEditUserView.getAddressTf().getText());
        newUser.setDepartment((String) addEditUserView.getDepartmentCb().getSelectedItem());
        newUser.setEmail(addEditUserView.getEmailTf().getText());
        newUser.setGrade(addEditUserView.getGradeTf().getText());
        newUser.setName(addEditUserView.getNameTf().getText());
        newUser.setPesel(addEditUserView.getPeselTf().getText());
        newUser.setPhoneNumber(addEditUserView.getPhoneTf().getText());
        newUser.setSurname(addEditUserView.getSurnameTf().getText());
        newUser.setUserName(addEditUserView.getUsernameTf().getText());
        newUser.setPrivilegeLevel(getSelectedPrivilegeLevel());
        return userManagementFacade.registerUser(newUser, "");
    }
    
    public Boolean assignUserToRoom(){
        Integer selectedRoom = addEditUserView.getRoomCb().getSelectedItem();
        return userManagementFacade.assignUserToRoom(addEditUserView.getUsernameTf().getText(), selectedRoom);
    }

    public void getSelectedUserData() {
        UserDTO userDetails = userManagementFacade.getUserDetails(ClientContext.getUsername());
        addEditUserView.getAddressTf().setText(userDetails.getAddress());
        addEditUserView.getDepartmentContentLabel().setText(userDetails.getDepartment());
        addEditUserView.getEmailTf().setText(userDetails.getEmail());
        addEditUserView.getGradeContentLabel().setText(userDetails.getGrade());
        addEditUserView.getNameTf().setText(userDetails.getName());
        addEditUserView.getPeselContentLabel().setText(userDetails.getPesel());
        addEditUserView.getPhoneTf().setText(userDetails.getPhoneNumber());
        addEditUserView.getSurnameTf().setText(userDetails.getSurname());
        addEditUserView.getUsernameContentLabel().setText(userDetails.getUserName());
        PrivilegeLevelDTO usersPrivilegeLevel = userManagementFacade.getUsersPrivilegeLevel(userDetails.getId().intValue());
        addEditUserView.getPermissionContentLabel().setText(usersPrivilegeLevel.getDescription());
    }

    public Boolean onChangeUserData() {
        UserDTO userDetails = userManagementFacade.getUserDetails(ClientContext.getUsername());
        UserDTO user = new UserDTO();
        if (!addEditUserView.getAddressTf().getText().isEmpty()) {
            user.setAddress(addEditUserView.getAddressTf().getText());
        } else {
            user.setAddress(userDetails.getAddress());
        }
        user.setDepartment(addEditUserView.getDepartmentContentLabel().getText());
        if (!addEditUserView.getEmailTf().getText().isEmpty()) {
            user.setEmail(addEditUserView.getEmailTf().getText());
        } else {
            user.setEmail(userDetails.getEmail());
        }
        user.setGrade(addEditUserView.getGradeContentLabel().getText());
        if (!addEditUserView.getNameTf().getText().isEmpty()) {
            user.setName(addEditUserView.getNameTf().getText());
        } else {
            user.setName(userDetails.getName());
        }
        user.setPesel(addEditUserView.getPeselContentLabel().getText());
        if (!addEditUserView.getPhoneTf().getText().isEmpty()) {
            user.setPhoneNumber(addEditUserView.getPhoneTf().getText());
        } else {
            user.setPhoneNumber(userDetails.getPhoneNumber());
        }
        if (!addEditUserView.getSurnameTf().getText().isEmpty()) {
            user.setSurname(addEditUserView.getSurnameTf().getText());
        } else {
            user.setSurname(userDetails.getSurname());
        }
        user.setUserName(addEditUserView.getUsernameContentLabel().getText());
        user.setId(userDetails.getId());
        return userFacade.changeUserDetails(user);
    }
    
}
