package pl.polsl.reservations.ejb.remote;

import java.util.ArrayList;
import pl.polsl.reservations.privileges.PrivilegeLevelEnum;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;

import pl.polsl.reservations.builder.DTOBuilder;
import pl.polsl.reservations.dto.PrivilegeLevelDTO;
import pl.polsl.reservations.dto.PrivilegeRequestDTO;
import pl.polsl.reservations.dto.UserDTO;
import pl.polsl.reservations.ejb.dao.*;
import pl.polsl.reservations.ejb.local.PrivilegeLevelRequestsQueue;
import pl.polsl.reservations.ejb.local.PrivilegeLevelRequestsQueueImpl;
import pl.polsl.reservations.ejb.local.UserContext;
import pl.polsl.reservations.entities.*;
import pl.polsl.reservations.interceptors.PrivilegeInterceptor;
import pl.polsl.reservations.logger.LoggerImpl;
import pl.polsl.reservations.privileges.PrivilegeRequest;

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

    PrivilegeLevelRequestsQueue levelRequestsQueue;

    private Users user;

    public UserFacadeImpl() {
        super();
        levelRequestsQueue = PrivilegeLevelRequestsQueueImpl.getInstance();
    }

    @Override
    public boolean login(String nameOrEmail, String password) {
        UserContext userContext = getCurrentUserContext();
        userContext.setPrivilegeLevel(PrivilegeLevelEnum.ADMIN);
        if (nameOrEmail.contains("@") && nameOrEmail.contains(".")) {
            if (usersFacade.validateUserByEmail(nameOrEmail, password)) {
                user = usersFacade.getUserByEmail(nameOrEmail);
                if (user == null) {
                    return false;
                }
                PriviligeLevels pl = user.getPriviligeLevel();
                List<Priviliges> priviligesCollection = pl.getPriviligesCollection();
                userContext.initialize(priviligesCollection, user);
                PrivilegeLevelEnum level = PrivilegeLevelEnum.getPrivilegeLevel(pl.getPriviligeLevel().intValue());
                userContext.setPrivilegeLevel(level);
                return true;
            }
        } else if (usersFacade.validateUser(nameOrEmail, password)) {
            user = usersFacade.getUserByUsername(nameOrEmail);
            if (user == null) {
                return false;
            }
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
            UserContext userContext = getCurrentUserContext();
            userContext.setPrivilegeLevel(PrivilegeLevelEnum.STANDARD_USER);
            userContext.setUser(new Users());
            return true;
        }
        return false;
    }

    @Override
    public PrivilegeLevelDTO getUserPriviligeLevel() {
        user = getCurrentUserContext().getUser();
        if (user == null) {
            PriviligeLevels pLevel = privilegeFacade.find(PrivilegeLevelEnum.STANDARD_USER.getValue());
            return DTOBuilder.buildPrivilegeLevelDTO(pLevel);
        } else {
            return DTOBuilder.buildPrivilegeLevelDTO(user.getPriviligeLevel());
        }
    }

    @Override
    public boolean changePassword(String oldPassword, String newPassword) {
        user = getCurrentUserContext().getUser();
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
        user = getCurrentUserContext().getUser();
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
        user = getCurrentUserContext().getUser();
        if (user == null || !Objects.equals(user.getId(), userDTO.getId())) {
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
        privilegeFacade.setUserContext(certificate);
        return certificateBean;

    }

    @Override
    public void removeCertificate(String certificate) {
        getUsersCertifcatesPool().removeCertificate(certificate);
    }

    @Override
    public PrivilegeLevelDTO getObtainablePrivilegeLevel() {
        if (user == null) {
            return null;
        }
        Integer adminLevel = PrivilegeLevelEnum.ADMIN.getValue();
        Long obtainableLevel = user.getPriviligeLevel().getPriviligeLevel() - 1;
        if (obtainableLevel > adminLevel) {
            return DTOBuilder.buildPrivilegeLevelDTO(privilegeFacade.getPrivligeLevelsEntityByLevelValue(obtainableLevel));
        } else {
            return null;
        }
    }

    @Override
    public boolean requestHigherPrivilegeLevel(String reason) {
        if (user == null) {
            return false;
        }
        PrivilegeLevelDTO obtainablePrivilegeLevel = getObtainablePrivilegeLevel();
        if (obtainablePrivilegeLevel == null) {
            return false;
        }
        if (isRequestingHigherPrivilegeLevel()) {
            return false;
        }
        PrivilegeRequest pr = new PrivilegeRequest(obtainablePrivilegeLevel.getPrivilegeLevel(),
                user.getId(), reason);
        return levelRequestsQueue.addRequest(pr);
    }

    @Override
    public boolean isRequestingHigherPrivilegeLevel() {
        if (user == null) {
            return false;
        }
        Long level = getObtainablePrivilegeLevel().getPrivilegeLevel();
        PrivilegeRequest pr = new PrivilegeRequest(level, user.getId(), "");
        return levelRequestsQueue.findRequest(pr);
    }

    @Override
    public List<UserDTO> getUsersWithLowerPrivilegeLevel() {
        user = getCurrentUserContext().getUser();
        if (user == null) {
            return null;
        }
        List<UserDTO> userList = new ArrayList<>();
        List<Users> users = usersFacade.findAll();
        for (Users u : users) {
            if (u.getPriviligeLevel().getPriviligeLevel() > user.getPriviligeLevel().getPriviligeLevel()
                    && u.getPriviligeLevel().getPriviligeLevel() < PrivilegeLevelEnum.STANDARD_USER.getValue()) {
                userList.add(DTOBuilder.buildUserDTO(user, user.getWorkers()));
            }
        }
        if (userList.isEmpty()) {
            return null;
        } else {
            return userList;
        }
    }

    @Override
    public List<PrivilegeRequestDTO> getOperationableRequests() {
        List<PrivilegeRequest> prList = levelRequestsQueue.getOperationableRequests(user.getPriviligeLevel().getPriviligeLevel());
        if (prList == null) {
            return null;
        }
        List<PrivilegeRequestDTO> prListDTO = new ArrayList<>();
        for (PrivilegeRequest pr : prList) {
            prListDTO.add(DTOBuilder.buildPrivilegeRequestDTO(pr));
        }
        return prListDTO;
    }
}
