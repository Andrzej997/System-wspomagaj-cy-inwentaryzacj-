package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import java.util.List;
import javax.ejb.Remote;
import pl.polsl.reservationsdatabasebeanremote.database.Users;
import pl.polsl.reservationsdatabasebeanremote.database.Workers;

/**
 * @author matis
 */
@Remote
public interface UsersFacadeRemote {

    void setPriviligeLevel(Integer level);

    void create(Users users);

    void edit(Users users);

    void remove(Users users);

    void merge(Users users);

    Users find(Object id);

    Users getReference(Object id);

    List<Users> findAll();

    List<Users> findRange(int[] range);

    int count();

    List<Users> findEntity(List<String> columnNames, List<Object> values);

    Boolean validateUser(String username, String password);

    Boolean validateUserByEmail(String email, String password);

    Long getUserPrivligeLevelByUsername(String username);

    Workers getWorkerByUsername(String username);

    Users getUserByUsername(String username);

    Users getUserByEmail(String email);
}
