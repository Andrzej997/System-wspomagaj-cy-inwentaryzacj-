package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebean.entities.Priviliges;

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

    void remove(Priviliges priviliges);

    void merge(Priviliges priviliges);

    Priviliges find(Object id);

    Priviliges getReference(Object id);

    List<Priviliges> findAll();

    List<Priviliges> findRange(int[] range);

    int count();

    public List<Priviliges> findEntity(List<String> columnNames, List<Object> values);

}
