package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Departaments;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author matis
 */
@Remote
public interface DepartamentsFacadeRemote {

    void setPriviligeLevel(Integer level);

    void create(Departaments departaments);

    void edit(Departaments departaments);

    void remove(Departaments departaments);

    void merge(Departaments departaments);

    Departaments find(Object id);

    Departaments getReference(Object id);

    List<Departaments> findAll();

    List<Departaments> findRange(int[] range);

    int count();

    public List<Departaments> findEntity(List<String> columnNames, List<Object> values);

}
