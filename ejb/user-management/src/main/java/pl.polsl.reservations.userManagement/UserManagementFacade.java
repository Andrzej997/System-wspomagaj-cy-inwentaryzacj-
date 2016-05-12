package pl.polsl.reservations.userManagement;

import java.util.HashMap;
import java.util.Map;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.UsersFacadeRemote;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import pl.polsl.reservationsdatabasebeanremote.database.Departaments;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.Workers;
import pl.polsl.reservationsdatabasebeanremote.database.Users;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.DepartamentsFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.WorkersFacadeRemote;

/**
 * Created by Krzysztof Stręk on 2016-05-09.
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

}
