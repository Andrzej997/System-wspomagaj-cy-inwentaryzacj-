package pl.polsl.reservations.client.mediators;

import java.util.List;
import pl.polsl.reservations.client.ClientContext;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.AddEditUserView;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.client.views.utils.AddUserEnum;
import pl.polsl.reservations.dto.DepartamentDTO;
import pl.polsl.reservations.dto.UserDTO;
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

    public AddEditUserViewMediator() {
        userFacade = (UserFacade) Lookup.getRemote("UserFacade");
        userManagementFacade = (UserManagementFacade) Lookup.getRemote("UserManagementFacade");
    }

    public AddEditUserView createView(MainView view, AddUserEnum option) {
        addEditUserView = new AddEditUserView(view, option, this);
        setDepartaments();
              if (option==AddUserEnum.EDIT){
            getSelectedUserData();
        }
        return addEditUserView;
    }

    public void getUsersList() {
        List<UserDTO> usersWithLowerPrivilegeLevel = userFacade.getUsersWithLowerPrivilegeLevel();
    }

    public void setDepartaments() {
        List<DepartamentDTO> departamentsCollection = userManagementFacade.getAllDepartaments();
        for (DepartamentDTO departamentDTO : departamentsCollection) {
            addEditUserView.getDepartmentCb().addItem(departamentDTO.getName());
        }
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
        return userManagementFacade.registerUser(newUser, "");
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
