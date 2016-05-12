package pl.polsl.reservations.userManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.UsersFacadeRemote;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import pl.polsl.reservationsdatabasebeanremote.database.Departaments;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.Workers;
import pl.polsl.reservationsdatabasebeanremote.database.Users;
import pl.polsl.reservationsdatabasebeanremote.database.PriviligeLevels;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.DepartamentsFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.WorkersFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.PriviligeLevelsFacadeRemote;

/**
 * Created by Krzysztof Strek on 2016-05-09.
 */
@Stateful(mappedName = "UserManagementFacade")
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
    public Map<String, String> getUserDetails(String userName) {
        Users user = usersFacade.getUserByUsername(userName);

        if (user == null) {
            return null;
        }

        Map<String, String> map = new HashMap<>();
        map.put("userName", user.getUsername());
        map.put("email", user.getEmail());
        map.put("phoneNumber", user.getPhoneNumber().toString());

        Workers worker = usersFacade.getWorkerByUsername(user.getUsername());
        if (worker != null) {
            map.put("name", worker.getWorkerName());
            map.put("surname", worker.getSurname());
            map.put("address", worker.getAdress());
            map.put("pesel", worker.getPesel());
            map.put("grade", worker.getGrade());
        }

        Room room = roomFacade.getReference(worker.getId());
        if (room != null) {
            map.put("roomNumber", room.getRoomNumber() + "");
        }

        Departaments departament = departamentsFacade.find(worker.getId());
        if (departament != null) {
            map.put("departamentName", departament.getDepratamentName());
        }

        return map;
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
     * @return
     */
    @Override
    public List<Map<String, String>> getAllPrivilegeLevels() {
        List<PriviligeLevels> levels = priviligeLevelsFacade.findAll();

        if (levels == null) {
            return null;
        }
        
        List<Map<String, String>> list = new ArrayList<>();
        for(PriviligeLevels level : levels) {
            Map<String, String> map = new HashMap<>();
            map.put("privilegeLevel", level.getPriviligeLevel().toString());
            map.put("description", level.getDescription());
            list.add(map);
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
    public Long getUsersPrivilegeLevel(String userName) {
        Users user = usersFacade.getUserByUsername(userName);
        
        if(user == null)
            return null;
        
        return user.getPriviligeLevel().getPriviligeLevel();
    }
    
    /**
     *
     * @param chiefName
     * @return
     */
    @Override
    public List<Map<String, String>> getUnderlings(String chiefName) {
        Workers chief = usersFacade.getWorkerByUsername(chiefName);
        List<Workers> workers = workersFacade.findAll();
        
        if(chief == null || workers == null)
            return null;
        
        List<Map<String, String>> list = new ArrayList<>();
        for(Workers worker : workers) {
            if(worker.getChiefId().getId().equals(chief.getId())) {
                Map<String, String> map = getUserDetails(usersFacade.getReference(worker.getId()).getUsername());
                list.add(map);
            }      
        }
        return list;
    }
}
