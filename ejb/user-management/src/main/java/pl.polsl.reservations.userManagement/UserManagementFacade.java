package pl.polsl.reservations.userManagement;

import pl.polsl.reservations.dto.PrivilegeLevelDTO;
import pl.polsl.reservations.dto.UserDTO;
import pl.polsl.reservationsdatabasebeanremote.database.PriviligeLevels;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.Users;
import pl.polsl.reservationsdatabasebeanremote.database.Workers;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.*;
import pl.polsl.reservationsdatabasebeanremote.database.logger.LoggerImpl;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.List;

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
}
