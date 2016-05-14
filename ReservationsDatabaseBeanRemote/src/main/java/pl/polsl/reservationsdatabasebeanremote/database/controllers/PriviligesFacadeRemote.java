package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.PriviligeLevels;
import pl.polsl.reservationsdatabasebeanremote.database.Priviliges;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author matis
 */
@Remote
public interface PriviligesFacadeRemote {

    void setPriviligeLevel(Integer level);

    void create(Priviliges priviliges);

    void edit(Priviliges priviliges);

    void remove(Priviliges entity);

    void merge(Priviliges priviliges);

    Priviliges find(Object id);

    Priviliges getReference(Object id);

    List<Priviliges> findAll();

    List<Priviliges> findRange(int[] range);

    int count();

    List<Priviliges> findEntity(List<String> columnNames, List<Object> values);

    List<PriviligeLevels> getPriviligeLevelsCollectionById(Number id);
}
