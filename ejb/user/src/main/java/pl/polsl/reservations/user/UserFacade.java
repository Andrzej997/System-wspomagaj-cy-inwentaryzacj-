package pl.polsl.reservations.user;

import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import pl.polsl.reservationsdatabasebeanremote.database.Departaments;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.Users;
import pl.polsl.reservationsdatabasebeanremote.database.Workers;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.DepartamentsFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.UsersFacadeRemote;
import pl.polsl.reservations.dto.UserDTO;

/**
 * Created by Krzysztof Stręk on 2016-05-07.
 */
@Stateful(mappedName = "UserFacade")
public class UserFacade implements UserFacadeRemote {
    
    @EJB
    private UsersFacadeRemote usersFacade;
    @EJB
    private RoomFacadeRemote roomFacade;
    @EJB
    private DepartamentsFacadeRemote departamentsFacade;

    private Users user = null;

    public UserFacade() {
    }

    @Override
    public boolean login(String nameOrEmail, String password) {
        if (nameOrEmail.contains("@") && nameOrEmail.contains(".")) {
            if (usersFacade.validateUserByEmail(nameOrEmail, password)) {
                user = usersFacade.getUserByEmail(nameOrEmail);
                return true;
            }
        } else {
            if (usersFacade.validateUser(nameOrEmail, password)) {
                user = usersFacade.getUserByUsername(nameOrEmail);
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean logout() {
        if (user != null) {
            user = null;
            return true;
        }
        return false;
    }


    @Override
    public Long getUserPrivilege() {
        if (user == null) {
            return null;
        } else {
            return usersFacade.getUserPrivligeLevelByUsername(user.getUsername());
        }
    }


    @Override
    public boolean changePassword(String oldPassword, String newPassword) {
        if (user == null) {
            return false;
        }
        if (user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            usersFacade.edit(user);
            return true;
        }
        return false;
    }

    @Override
    public UserDTO getUserDetails() {
        if (user == null) {
            return null;
        }

        return new UserDTO(user);
    }

}
