package pl.polsl.reservations.ejb.remote;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;

import pl.polsl.reservations.annotations.RequiredPrivilege;
import pl.polsl.reservations.builder.DTOBuilder;
import pl.polsl.reservations.dto.DepartamentDTO;
import pl.polsl.reservations.dto.InstituteDTO;
import pl.polsl.reservations.dto.PrivilegeLevelDTO;
import pl.polsl.reservations.dto.UserDTO;
import pl.polsl.reservations.ejb.dao.*;
import pl.polsl.reservations.entities.Departaments;
import pl.polsl.reservations.entities.Institutes;
import pl.polsl.reservations.entities.PriviligeLevels;
import pl.polsl.reservations.entities.Room;
import pl.polsl.reservations.entities.Users;
import pl.polsl.reservations.entities.Workers;
import pl.polsl.reservations.interceptors.PrivilegeInterceptor;
import pl.polsl.reservations.logger.LoggerImpl;
import pl.polsl.reservations.privileges.PrivilegeEnum;

/**
 * Created by Krzysztof Strek on 2016-05-09.
 */
@Stateful(mappedName = "UserManagementFacade")
@Interceptors({LoggerImpl.class, PrivilegeInterceptor.class})
public class UserManagementFacadeImpl extends AbstractBusinessFacadeImpl implements UserManagementFacade {

    @EJB
    UsersDao usersFacade;
    @EJB
    RoomDao roomFacade;
    @EJB
    DepartamentsDao departamentsFacade;
    @EJB
    WorkersDao workersFacade;
    @EJB
    PriviligeLevelsDao priviligeLevelsFacade;
    @EJB
    InstitutesDao institutesFacade;

    public UserManagementFacadeImpl() {
        super();
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.MANAGE_TECH_CHEF_SUBORDINATES)
    public UserDTO getUserDetails(String userName) {
        Users user = usersFacade.getUserByUsername(userName);

        if (user == null) {
            return null;
        }
        Workers worker = workersFacade.find(user.getId());
        return DTOBuilder.buildUserDTO(user, worker);
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.MANAGE_TECH_CHEF_SUBORDINATES)
    public UserDTO getUserDetails(int userId) {
        Users user = usersFacade.getReference(userId);

        if (user == null) {
            return null;
        }
        Workers worker = workersFacade.find(user.getId());
        return DTOBuilder.buildUserDTO(user, worker);
    }

    /**
     * Przypisuje szefa uzytkownikowi na podstawie ich nazw uzytkownika.
     *
     * @param userName nazwa uzytkownika ktoremu przypisany jest szef
     * @param chiefName nazwa uzytkownika szefa
     * @return true jesli sie udalo, false jesli nie
     */
    @Override
    @RequiredPrivilege(PrivilegeEnum.MANAGE_TECH_CHEF_SUBORDINATES)
    public boolean assignUserToChief(String userName, String chiefName) {
        Workers user = usersFacade.getWorkerByUsername(userName);
        Workers chief = usersFacade.getWorkerByUsername(chiefName);

        if (user == null || chief == null) {
            return false;
        }

        //TODO poziom uprawnien
        user.setChief(chief.getChief());
        workersFacade.edit(user);
        return true;
    }

    /**
     * Przypisanie pracownika o danej nazwie uzytkownika do pokoju o podanym
     * numerze.
     *
     * @param userName nazwa uzytkownik
     * @param roomNumber numer pokoju
     * @return true jesli sie uda, false jesli nie
     */
    @Override
    public boolean assignUserToRoom(String userName, int roomNumber) {
        Room room = roomFacade.getRoomByNumber(roomNumber);
        Workers worker = usersFacade.getWorkerByUsername(userName);

        if (room == null || worker == null) {
            return false;
        }

        worker.setRoom(room);
        workersFacade.edit(worker);
        //TODO poziom uprawnien

        return true;
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.MANAGE_INSTITUTE_WORKERS)
    public boolean assignUserToDepartament(String userName, Long departamentId) {
        Workers worker = usersFacade.getWorkerByUsername(userName);
        Departaments departament = departamentsFacade.find(departamentId);

        if (worker == null || departament == null) {
            return false;
        }

        worker.setDepartament(departament);
        workersFacade.edit(worker);
        return true;
    }

    /**
     * Dostepne pola mapy: privilegeLevel i description.
     *
     * @return
     */
    @Override
    public List<PrivilegeLevelDTO> getAllPrivilegeLevels() {
        List<PriviligeLevels> levels = priviligeLevelsFacade.findAll();

        if (levels == null) {
            return null;
        }

        List<PrivilegeLevelDTO> list = new ArrayList<>();
        for (PriviligeLevels level : levels) {
            list.add(DTOBuilder.buildPrivilegeLevelDTO(level));
        }
        return list;
    }

    @Override
    public boolean changePrivilegeLevel(String userName, Long privilegeLevel) {
        Users user = usersFacade.getUserByUsername(userName);
        List<PriviligeLevels> levels = priviligeLevelsFacade.findAll();

        if (user == null || levels == null) {
            return false;
        }

        PriviligeLevels found = null;
        for (PriviligeLevels level : levels) {
            if (level.getPriviligeLevel().equals(privilegeLevel)) {
                found = level;
                break;
            }
        }
        if (found == null) {
            return false;
        }

        user.setPriviligeLevel(found);
        usersFacade.edit(user);

        return true;
    }

    @Override
    public List<DepartamentDTO> getAllDepartaments() {
        List<Departaments> departamentsList = departamentsFacade.findAll();
        if (departamentsList == null) {
            return null;
        }

        List<DepartamentDTO> list = new ArrayList<>();
        for (Departaments d : departamentsList) {
            list.add(DTOBuilder.buildDepartamentDTO(d));
        }
        return list;
    }

    @Override
    public List<InstituteDTO> getAllInstitutes() {
        List<Institutes> institutesList = institutesFacade.findAll();
        if (institutesList == null) {
            return null;
        }

        List<InstituteDTO> list = new ArrayList<>();
        for (Institutes i : institutesList) {
            list.add(DTOBuilder.buildInstituteDTO(i));
        }
        return list;
    }

    @Override
    public PrivilegeLevelDTO getUsersPrivilegeLevel(String userName) {
        Users user = usersFacade.getUserByUsername(userName);

        if (user == null) {
            return null;
        }
        PriviligeLevels priviligeLevel = user.getPriviligeLevel();
        return DTOBuilder.buildPrivilegeLevelDTO(priviligeLevel);
    }

    @Override
    public PrivilegeLevelDTO getUsersPrivilegeLevel(int userId) {
        Users user = usersFacade.getReference(userId);

        if (user == null) {
            return null;
        }
        PriviligeLevels priviligeLevel = user.getPriviligeLevel();
        return DTOBuilder.buildPrivilegeLevelDTO(priviligeLevel);
    }

    /**
     *
     * @param chiefName
     * @return
     */
    @Override
    public List<UserDTO> getUnderlings(String chiefName) {
        Workers chief = usersFacade.getWorkerByUsername(chiefName);
        List<Workers> workers = workersFacade.findAll();

        if (chief == null || workers == null) {
            return null;
        }

        List<UserDTO> list = new ArrayList<>();
        for (Workers worker : workers) {
            if (worker.getChief().getId().equals(chief.getId())) {
                UserDTO user = getUserDetails(worker.getId().intValue());
                list.add(user);
            }
        }
        return list;
    }

    @Override
    public List<UserDTO> getUnderlings(int chiefId) {
        Workers chief = workersFacade.getReference(chiefId);
        List<Workers> workers = workersFacade.findAll();

        if (chief == null || workers == null) {
            return null;
        }

        List<UserDTO> list = new ArrayList<>();
        for (Workers worker : workers) {
            if (worker.getChief().getId().equals(chief.getId())) {
                UserDTO user = getUserDetails(worker.getId().intValue());
                list.add(user);
            }
        }
        return list;
    }

    /**
     *
     * @param user
     * @return true jesli istnieje
     */
    @Override
    public boolean checkUserExistence(UserDTO user) {
        Users userDB;
        userDB = usersFacade.getReference(user.getId());
        if (userDB != null) {
            return true;
        }
        userDB = usersFacade.getUserByEmail(user.getEmail());
        if (userDB != null) {
            return true;
        }
        userDB = usersFacade.getUserByUsername(user.getUserName());
        if (userDB != null) {
            return true;
        }
        return false;
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.ADMIN_ACTIONS)
    public boolean registerUser(UserDTO user, String password) {
        if (checkUserExistence(user)) {
            return false;
        }

        Users userDB = new Users();

        userDB.setPassword(password);

        userDB.setEmail(user.getEmail());
        userDB.setPhoneNumber(Long.parseLong(user.getPhoneNumber()));
        userDB.setUsername(user.getUserName());

        PriviligeLevels level = priviligeLevelsFacade.getPrivligeLevelsEntityByLevelValue(user.getPrivilegeLevel());
        if (level != null) {
            userDB.setPriviligeLevel(level);
        }

        usersFacade.create(userDB);

        Workers workerDB = new Workers();

        workerDB.setAdress(user.getAddress());
        workerDB.setGrade(user.getGrade());
        workerDB.setPesel(user.getPesel());
        workerDB.setSurname(user.getSurname());
        workerDB.setWorkerName(user.getName());

        Departaments departaments = departamentsFacade.getDepartamentByName(user.getDepartment());
        if (departaments != null) {
            workerDB.setDepartament(departaments);
        }

        workersFacade.create(workerDB);

        return true;
    }

    @Override
    public Boolean certificateBean(String certificate) {
        Boolean certificateBean = super.certificateBean(certificate);
        usersFacade.setUserContext(certificate);
        roomFacade.setUserContext(certificate);
        departamentsFacade.setUserContext(certificate);
        workersFacade.setUserContext(certificate);
        priviligeLevelsFacade.setUserContext(certificate);
        institutesFacade.setUserContext(certificate);
        return certificateBean;
    }

}
