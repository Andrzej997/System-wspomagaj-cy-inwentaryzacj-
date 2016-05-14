package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.PriviligeLevels;
import pl.polsl.reservationsdatabasebeanremote.database.Priviliges;
import pl.polsl.reservationsdatabasebeanremote.database.Users;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author matis
 */
@Remote
public interface PriviligeLevelsFacadeRemote {

    void setPriviligeLevel(Integer level);

    void create(PriviligeLevels priviligeLevels);

    void edit(PriviligeLevels priviligeLevels);

    void remove(PriviligeLevels entity);

    void merge(PriviligeLevels priviligeLevels);

    PriviligeLevels find(Object id);

    PriviligeLevels getReference(Object id);

    List<PriviligeLevels> findAll();

    List<PriviligeLevels> findRange(int[] range);

    int count();

    List<PriviligeLevels> findEntity(List<String> columnNames, List<Object> values);

    List<Priviliges> getPriviligesCollectionById(Number id);

    List<Users> getUsersCollectionById(Number id);
    
    PriviligeLevels getPrivligeLevelsEntityByLevelValue(Long levelValue);
}
