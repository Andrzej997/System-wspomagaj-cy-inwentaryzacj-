package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import java.util.List;
import javax.ejb.Remote;
import pl.polsl.reservationsdatabasebeanremote.database.PriviligeLevels;

/**
 * @author matis
 */
@Remote
public interface PriviligeLevelsFacadeRemote {

    void setPriviligeLevel(Integer level);

    void create(PriviligeLevels priviligeLevels);

    void edit(PriviligeLevels priviligeLevels);

    void remove(PriviligeLevels priviligeLevels);

    void merge(PriviligeLevels priviligeLevels);

    PriviligeLevels find(Object id);

    PriviligeLevels getReference(Object id);

    List<PriviligeLevels> findAll();

    List<PriviligeLevels> findRange(int[] range);

    int count();

    public List<PriviligeLevels> findEntity(List<String> columnNames, List<Object> values);

}
