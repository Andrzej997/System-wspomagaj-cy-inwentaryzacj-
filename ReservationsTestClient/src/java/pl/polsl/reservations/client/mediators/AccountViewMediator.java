
package pl.polsl.reservations.client.mediators;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JOptionPane;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.AccountView;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.client.views.WeekDataView;
import pl.polsl.reservations.dto.RoomDTO;
import pl.polsl.reservations.roomManagement.RoomManagementFacade;
import pl.polsl.reservations.user.UserFacade;

/**
 *
 * @author matis
 */
public class AccountViewMediator {
    
    private AccountView accountView;
    private final UserFacade userFacade;
    private final RoomManagementFacade roomManagementFacade;
       
    public AccountViewMediator(){
        userFacade = (UserFacade) Lookup.getRemote("UserFacade");
        roomManagementFacade = (RoomManagementFacade) Lookup.getRemote("RoomManagementFacade");
    }
    
    public AccountView createView(MainView parent){
        accountView = new AccountView(parent, this);
        if(userFacade.getUserPrivilege() != 1){
           accountView.getAddButton().setVisible(false);
        }
        getRoomData();
        return accountView;
    }
    
    public AccountView getAccountView() {
        return accountView;
    }
    
    public void dispatchRoomClickEvent(ActionEvent evt){
        accountView.getWindow()
                .setView(new WeekDataViewMediator().createView(accountView.getWindow(), 
                        accountView.getChooseRoomDropdown().getSelectedItem()));
    }
    
    public void dispatchChangePasswordClickEvent(ActionEvent evt){
        String oldPassword = JOptionPane.showInputDialog("Type old password");
        String password = JOptionPane.showInputDialog("Type new password");
        String passwordConfirm = JOptionPane.showInputDialog("Confirm password");
        if (!password.equals(passwordConfirm)) {
            JOptionPane.showMessageDialog(accountView, "Passwords do not match.");
        }
        if(oldPassword.equals(password)){
            JOptionPane.showMessageDialog(accountView, "Password is the same as old password");
        }
        if(userFacade.changePassword(oldPassword, password)){
            JOptionPane.showMessageDialog(accountView, "Password successfully changed!");
        } else {
            JOptionPane.showMessageDialog(accountView, "Password was not changed");
        }
    }
    
    public void dispatchAddUserEvent(ActionEvent evt){
        if(userFacade.getUserPrivilege() == 1){
            
        }
    }

    public void getRoomData() {
        List<RoomDTO> roomsList = roomManagementFacade.getRoomsList();
        for(RoomDTO roomDTO : roomsList){
            accountView.getChooseRoomDropdown().addItem(roomDTO.getNumber());
        }
    }
    
}