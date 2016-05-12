package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import java.util.List;
import javax.ejb.Remote;
import pl.polsl.reservationsdatabasebeanremote.database.PriviligeLevels;
import pl.polsl.reservationsdatabasebeanremote.database.Priviliges;
import pl.polsl.reservationsdatabasebeanremote.database.Users;

/**
 * @author matis
 */
@Remote
public interface PriviligeLevelsFacadeRemote {

    void setPriviligeLevel(Integer level);

    void create(PriviligeLevels priviligeLevels);

    void edit(PriviligeLevels priviligeLevels);

    void remove(Object id);

    void merge(PriviligeLevels priviligeLevels);

    PriviligeLevels find(Object id);

    PriviligeLevels getReference(Object id);

    List<PriviligeLevels> findAll();

    List<PriviligeLevels> findRange(int[] range);

    int count();

    public List<PriviligeLevels> findEntity(List<String> columnNames, List<Object> values);

    List<Priviliges> getPriviligesCollectionById(Number id);

    List<Users> getUsersCollectionById(Number id);
}
