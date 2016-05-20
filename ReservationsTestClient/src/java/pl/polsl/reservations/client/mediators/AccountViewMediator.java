package pl.polsl.reservations.client.mediators;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.AccountView;
import pl.polsl.reservations.client.views.MainWindow;
import pl.polsl.reservations.client.views.WeekDataView;
import pl.polsl.reservations.user.UserFacade;

/**
 *
 * @author matis
 */
public class AccountViewMediator {
    
    private AccountView accountView;
    private final UserFacade userFacade;
       
    public AccountViewMediator(){
        userFacade = (UserFacade) Lookup.getRemote("UserFacade");
    }
    
    public AccountView createView(MainWindow parent){
        accountView = new AccountView(parent, this);
        if(userFacade.getUserPrivilege() != 1){
           accountView.getAddButton().setVisible(false);
        }
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
    
}
