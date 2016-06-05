package pl.polsl.reservations.client.mediators;

import java.util.List;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.AddEditUserView;
import pl.polsl.reservations.client.views.MainView;
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
    
    public AddEditUserViewMediator(){
        userFacade = (UserFacade) Lookup.getRemote("UserFacade");
        userManagementFacade = (UserManagementFacade) Lookup.getRemote("UserManagementFacade");
    }
    
    public AddEditUserView createView(MainView view, boolean editUser){
        addEditUserView = new AddEditUserView(view, editUser, this);
        setDepartaments();
        return addEditUserView;
    }
   
    public void getUsersList(){
        List<UserDTO> usersWithLowerPrivilegeLevel = userFacade.getUsersWithLowerPrivilegeLevel();
    }
    
    public void setDepartaments(){
        List<DepartamentDTO> departamentsCollection = userManagementFacade.getAllDepartaments();
        for(DepartamentDTO departamentDTO : departamentsCollection){
            addEditUserView.getDepartmentCb().addItem(departamentDTO.getName());
        }
    }
    
    public void onAddUser(){
        UserDTO newUser = new UserDTO();
        newUser.setAddress(addEditUserView.getAddressTf().getText());
        newUser.setDepartment((String)addEditUserView.getDepartmentCb().getSelectedItem());
        newUser.setEmail(addEditUserView.getEmailTf().getText());
        newUser.setGrade(addEditUserView.getGradeTf().getText());
        newUser.setName(addEditUserView.getNameTf().getText());
        newUser.setPesel(addEditUserView.getPeselTf().getText());
        newUser.setPhoneNumber(addEditUserView.getPhoneTf().getText());
        newUser.setSurname(addEditUserView.getSurnameTf().getText());
        newUser.setUserName(addEditUserView.getUsernameTf().getText());
        userManagementFacade.registerUser(newUser, "change");
    }
    
    public void getSelectedUserData(){
        UserDTO userDetails = userManagementFacade.getUserDetails("");
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
}
