package pl.polsl.reservations.ejb.remote;

import pl.polsl.reservations.ejb.dao.*;
import pl.polsl.reservations.ejb.local.UserContext;
import pl.polsl.reservations.dto.UserDTO;
import pl.polsl.reservations.entities.*;
import pl.polsl.reservations.logger.LoggerImpl;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-05-07.
 */
@Stateful(mappedName = "UserFacade")
@Interceptors({LoggerImpl.class})
public class UserFacadeImpl implements UserFacade {

    @EJB
    private UsersDao usersFacade;
    @EJB
    private RoomDao roomFacade;
    @EJB
    private DepartamentsDao departamentsFacade;
    @EJB
    private WorkersDao workersFacade;
    @EJB
    private PriviligeLevelsDao privilegeLevelsDao;
    @Inject
    private UserContext userContext;

    private Users user = null;

    public UserFacadeImpl() {
    }

    @Override
    public boolean login(String nameOrEmail, String password) {
        userContext.setPrivilegeLevel(PrivilegeLevelEnum.ADMIN);
        if (nameOrEmail.contains("@") && nameOrEmail.contains(".")) {
            if (usersFacade.validateUserByEmail(nameOrEmail, password)) {
                user = usersFacade.getUserByEmail(nameOrEmail);
                PriviligeLevels pl = user.getPriviligeLevel();
                List<Priviliges> list = privilegeLevelsDao.getPriviligesCollectionById(pl.getPriviligeLevel());
                userContext.initialize(list);
                userContext.setPrivilegeLevel(PrivilegeLevelEnum.getPrivilegeLevel(pl.getPriviligeLevel().intValue()));
                return true;
            }
        } else {
            if (usersFacade.validateUser(nameOrEmail, password)) {
                user = usersFacade.getUserByUsername(nameOrEmail);
                userContext.initialize(user.getPriviligeLevel().getPriviligesCollection());
                return true;
            }
        }
        userContext.setPrivilegeLevel(PrivilegeLevelEnum.STANDARD_USER);
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
            return 6l;
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

    /**
     * Zmienia email, numer telefonu, adres, stopien naukowy, pesel, nazwisko i
     * imie.
     *
     * @param userDTO
     * @return
     */
    @Override
    public boolean changeUserDetails(UserDTO userDTO) {

        if (user == null || user.getUserId() != userDTO.getId()) {
            return false;
        }

        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(Long.parseLong(userDTO.getPhoneNumber()));

        usersFacade.edit(user);

        Workers workerDB = workersFacade.getReference(user.getUserId());

        workerDB.setAdress(userDTO.getAddress());
        workerDB.setGrade(userDTO.getGrade());
        workerDB.setPesel(userDTO.getPesel());
        workerDB.setSurname(userDTO.getSurname());
        workerDB.setWorkerName(userDTO.getName());

        workersFacade.edit(workerDB);

        return true;
    }

    @Override
    public boolean loginAsGuest() {
        
        //za�lepka
        user = new Users();
        return true;
    }
}
