package pl.polsl.reservations.user;

import java.util.HashMap;
import java.util.Map;
import pl.polsl.reservationsdatabasebeanremote.database.Users;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.ReservationsFacadeRemote;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import pl.polsl.reservationsdatabasebeanremote.database.Departaments;
import pl.polsl.reservationsdatabasebeanremote.database.Workers;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.DepartamentsFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.UsersFacadeRemote;

/**
 * Created by Krzysztof Stręk on 2016-05-07.
 */
@Stateful(mappedName = "UserFacade")
public class UserFacade implements UserFacadeRemote {

    /*@EJB
     ReservationsFacadeRemote reservation;

     @Override
     public int getUser() {
     List<Reservations> list =  reservation.findAll();
     return list.size();
     }*/
    @EJB
    private UsersFacadeRemote usersFacade;
    @EJB
    private RoomFacadeRemote roomFacade;
    @EJB
    private DepartamentsFacadeRemote departamentsFacade;

    private Users user = null;

    /**
     * Loguje u�ytkownika. Automatycznie wykrywa czy u�yty zosta� login czy
     * e-mail.
     *
     * @param nameOrEmail nazwa u�ytkownika lub e-mail
     * @param password has�o
     * @return true je�li zalogowano, false je�li nie
     */
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

    /**
     * Wylogowuje u�ytkownika.
     *
     * @return true je�eli uzytkownik by� zalogowany, false je�eli nie
     */
    @Override
    public boolean logout() {
        if (user != null) {
            user = null;
            return true;
        }
        return false;
    }

    /**
     * Zwraca poziom uprawnie� zalogowanego u�ytkownika. Gdy u�ytkownik jest
     * niezalogowany zwraca null.
     *
     * @return liczba reprezentuj�ca poziom uprawnie� lub null, gdy z jakiego�
     * powodu nie ma uprawnie�
     */
    @Override
    public Long getUserPrivilege() {
        if (user == null) {
            return null;
        } else {
            return usersFacade.getUserPrivligeLevelByUsername(user.getUsername());
        }
    }

    /**
     * Zmiana has�a zalogowanego u�ytkownika.
     *
     * @param oldPassword stare has�o
     * @param newPassword nowe has�o
     * @return true je�li zmieniono has�o, w przeciwnym razie false
     */
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

    /**
     * Dost�pne pola w mapie: userName, email, phoneNumber, name, surname,
     * address, pesel, grade, roomNumber i departamentName.
     *
     * @return mapa z danymi o u�ytkowniku i danymi pracowniczymi, b�d� null
     */
    @Override
    public Map<String, String> getUserDetails() {
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

}
