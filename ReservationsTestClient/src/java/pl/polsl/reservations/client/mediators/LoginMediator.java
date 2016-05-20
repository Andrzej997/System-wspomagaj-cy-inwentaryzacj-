/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.reservations.client.mediators;

import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.dto.UserDTO;
import pl.polsl.reservations.user.UserFacade;
import pl.polsl.reservations.client.views.LoginView;
import pl.polsl.reservations.client.views.MainView;

/**
 *
 * @author Pawe�
 */
public class LoginMediator {

    private UserFacade userFacade;
    private LoginView loginWindow;
    private UserDTO userDTO;

    public LoginMediator() {
        userFacade = (UserFacade) Lookup.getRemote("UserFacade");
    }

    public boolean getUserData(String userName, String password) {
        return userFacade.login(userName, password);
    }

    public LoginView createView(MainView parent){
         loginWindow = new LoginView(parent,this);
 
             loginWindow.getRegisterButton().setVisible(false);

         return loginWindow;
    }
    
}
