package pl.polsl.reservations.ejb.remote;

import pl.polsl.reservations.privileges.PrivilegeLevelEnum;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;

import pl.polsl.reservations.builder.DTOBuilder;
import pl.polsl.reservations.dto.PrivilegeLevelDTO;
import pl.polsl.reservations.dto.UserDTO;
import pl.polsl.reservations.ejb.dao.*;
import pl.polsl.reservations.ejb.local.UserContext;
import pl.polsl.reservations.entities.*;
import pl.polsl.reservations.interceptors.PrivilegeInterceptor;
import pl.polsl.reservations.logger.LoggerImpl;

/**
 * Created by Krzysztof Stręk on 2016-05-07.
 */
@Stateful(mappedName = "UserFacade")
@Interceptors({LoggerImpl.class, PrivilegeInterceptor.class})
public class UserFacadeImpl extends AbstractBusinessFacadeImpl implements UserFacade {

    @EJB
    private UsersDao usersFacade;
    @EJB
    private RoomDao roomFacade;
    @EJB
    private DepartamentsDao departamentsFacade;
    @EJB
    private WorkersDao workersFacade;
    @EJB
    private PriviligeLevelsDao privilegeFacade;
    
    private Users user = null;

    public UserFacadeImpl() {
        super();
    }

    @Override
    public boolean login(String nameOrEmail, String password) {
        UserContext userContext = getCurrentUserContext();
        userContext.setPrivilegeLevel(PrivilegeLevelEnum.ADMIN);
        if (nameOrEmail.contains("@") && nameOrEmail.contains(".")) {
            if (usersFacade.validateUserByEmail(nameOrEmail, password)) {
                user = usersFacade.getUserByEmail(nameOrEmail);
                PriviligeLevels pl = user.getPriviligeLevel();
                List<Priviliges> priviligesCollection = pl.getPriviligesCollection();
                userContext.initialize(priviligesCollection, user);
                PrivilegeLevelEnum level = PrivilegeLevelEnum.getPrivilegeLevel(pl.getPriviligeLevel().intValue());
                userContext.setPrivilegeLevel(level);
                return true;
            }
        } else if (usersFacade.validateUser(nameOrEmail, password)) {
            user = usersFacade.getUserByUsername(nameOrEmail);
            userContext.initialize(user.getPriviligeLevel().getPriviligesCollection(), user);
            return true;
        }
        userContext.setPrivilegeLevel(PrivilegeLevelEnum.STANDARD_USER);
        return false;
    }

    @Override
    public boolean loginAsGuest() {
        UserContext userContext = getCurrentUserContext();
        userContext.setPrivilegeLevel(PrivilegeLevelEnum.STANDARD_USER);
        user = new Users();
        return true;
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
    public PrivilegeLevelDTO getUserPriviligeLevel() {
        if (user == null) {
            PriviligeLevels pLevel = privilegeFacade.find(PrivilegeLevelEnum.STANDARD_USER.getValue());
            return DTOBuilder.buildPrivilegeLevelDTO(pLevel);
        } else {
            return DTOBuilder.buildPrivilegeLevelDTO(user.getPriviligeLevel());
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
        Workers worker = workersFacade.find(user.getId());
        return DTOBuilder.buildUserDTO(user, worker);
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

        if (user == null || user.getId() != userDTO.getId()) {
            return false;
        }

        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(Long.parseLong(userDTO.getPhoneNumber()));

        usersFacade.edit(user);

        Workers workerDB = workersFacade.getReference(user.getId());

        workerDB.setAdress(userDTO.getAddress());
        workerDB.setGrade(userDTO.getGrade());
        workerDB.setPesel(userDTO.getPesel());
        workerDB.setSurname(userDTO.getSurname());
        workerDB.setWorkerName(userDTO.getName());

        workersFacade.edit(workerDB);

        return true;
    }

    @Override
    public Boolean certificateBean(String certificate) {
        Boolean certificateBean = super.certificateBean(certificate);
        usersFacade.setUserContext(certificate);
        roomFacade.setUserContext(certificate);
        departamentsFacade.setUserContext(certificate);
        workersFacade.setUserContext(certificate);
        return certificateBean;

    }

    @Override
    public void removeCertificate(String certificate) {
        getUsersCertifcatesPool().removeCertificate(certificate);
    }

}
