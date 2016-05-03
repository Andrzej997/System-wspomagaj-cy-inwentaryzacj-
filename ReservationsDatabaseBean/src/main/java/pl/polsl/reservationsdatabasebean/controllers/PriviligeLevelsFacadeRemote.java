package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebean.entities.PriviligeLevels;

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

    void remove(PriviligeLevels priviligeLevels);

    void merge(PriviligeLevels priviligeLevels);

    PriviligeLevels find(Object id);

    PriviligeLevels getReference(Object id);

    List<PriviligeLevels> findAll();

    List<PriviligeLevels> findRange(int[] range);

    int count();

    public List<PriviligeLevels> findEntity(List<String> columnNames, List<Object> values);

}
