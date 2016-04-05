package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import java.util.List;
import javax.ejb.Remote;
import pl.polsl.reservationsdatabasebeanremote.database.Users;

/**
 *
 * @author matis
 */
@Remote
public interface UsersFacadeRemote {

    void create(Users users);

    void edit(Users users);

    void remove(Users users);
    
    void merge(Users users);

    Users find(Object id);

    List<Users> findAll();

    List<Users> findRange(int[] range);

    int count();
    
    public List<Users> findEntity(List<String> columnNames, List<Object> values);
    
}
