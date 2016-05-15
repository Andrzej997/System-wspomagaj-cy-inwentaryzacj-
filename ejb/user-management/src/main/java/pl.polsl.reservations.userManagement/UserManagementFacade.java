package pl.polsl.reservations.userManagement;

import pl.polsl.reservations.dto.PrivilegeLevelDTO;
import pl.polsl.reservations.dto.UserDTO;
import pl.polsl.reservationsdatabasebeanremote.database.PriviligeLevels;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.Users;
import pl.polsl.reservationsdatabasebeanremote.database.Workers;
import pl.polsl.reservationsdatabasebeanremote.database.Departaments;
import pl.polsl.reservationsdatabasebeanremote.database.Institutes;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.*;
import pl.polsl.reservationsdatabasebeanremote.database.logger.LoggerImpl;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.List;
import pl.polsl.reservations.dto.DepartamentDTO;
import pl.polsl.reservations.dto.InstituteDTO;

/**
 * Created by Krzysztof Strek on 2016-05-09.
 */
@Stateful(mappedName = "UserManagementFacade")
@Interceptors({LoggerImpl.class})
public class UserManagementFacade implements UserManagementFacadeRemote {

    @EJB
    UsersFacadeRemote usersFacade;
    @EJB
    RoomFacadeRemote roomFacade;
    @EJB
    DepartamentsFacadeRemote departamentsFacade;
    @EJB
    WorkersFacadeRemote workersFacade;
    @EJB
    PriviligeLevelsFacadeRemote priviligeLevelsFacade;
    @EJB
    InstitutesFacadeRemote institutesFacade;

    public UserManagementFacade() {
    }

    @Override
    public UserDTO getUserDetails(String userName) {
        Users user = usersFacade.getUserByUsername(userName);

        if (user == null) {
            return null;
        }

        return new UserDTO(user);
    }

    @Override
    public UserDTO getUserDetails(int userId) {
        Users user = usersFacade.getReference(userId);

        if (user == null) {
            return null;
        }

        return new UserDTO(user);
    }

    /**
     * Przypisuje szefa uzytkownikowi na podstawie ich nazw uzytkownika.
     *
     * @param userName nazwa uzytkownika ktoremu przypisany jest szef
     * @param chiefName nazwa uzytkownika szefa
     * @return true jesli sie udalo, false jesli nie
     */
    @Override
    public boolean assignUserToChief(String userName, String chiefName) {
        Workers user = usersFacade.getWorkerByUsername(userName);
        Workers chief = usersFacade.getWorkerByUsername(chiefName);

        if (user == null || chief == null) {
            return false;
        }

        //TODO poziom uprawnien
        user.setChiefId(chief.getChiefId());
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
    public boolean assignUserToDepartament(String userName, Long departamentId) {
        Workers worker = usersFacade.getWorkerByUsername(userName);
        Departaments departament = departamentsFacade.find(departamentId);
        
        if(worker == null || departament == null)
            return false;
        
        worker.setDepartamentId(departament);
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
            list.add(new PrivilegeLevelDTO(level));
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
        if(departamentsList == null)
            return null;
        
        List<DepartamentDTO> list = new ArrayList<>();
        for(Departaments d : departamentsList) {
            list.add(new DepartamentDTO(d));
        }
        return list;
    }

    @Override
    public List<InstituteDTO> getAllInstitutes() {
        List<Institutes> institutesList = institutesFacade.findAll();
        if(institutesList == null)
            return null;
        
        List<InstituteDTO> list = new ArrayList<>();
        for(Institutes i : institutesList) {
            list.add(new InstituteDTO(i));
        }
        return list;
    }

    @Override
    public PrivilegeLevelDTO getUsersPrivilegeLevel(String userName) {
        Users user = usersFacade.getUserByUsername(userName);

        if (user == null) {
            return null;
        }

        return new PrivilegeLevelDTO(user.getPriviligeLevel());
    }

    @Override
    public PrivilegeLevelDTO getUsersPrivilegeLevel(int userId) {
        Users user = usersFacade.getReference(userId);

        if (user == null) {
            return null;
        }

        return new PrivilegeLevelDTO(user.getPriviligeLevel());
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
            if (worker.getChiefId().getId().equals(chief.getId())) {
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
            if (worker.getChiefId().getId().equals(chief.getId())) {
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
        userDB.setPriviligeLevel(level);

        usersFacade.create(userDB);

        Workers workerDB = new Workers();

        workerDB.setAdress(user.getAddress());
        workerDB.setGrade(user.getGrade());
        workerDB.setPesel(user.getPesel());
        workerDB.setSurname(user.getSurname());
        workerDB.setWorkerName(user.getName());

        Departaments departaments = departamentsFacade.getDepartamentByName(user.getDepartment());
        if (departaments != null) {
            workerDB.setDepartamentId(departaments);
        }
        
        workersFacade.create(workerDB);

        return true;
    }
}
