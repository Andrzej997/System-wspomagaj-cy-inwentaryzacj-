package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Departaments;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.Workers;

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

    void remove(Departaments entity);

    void merge(Departaments departaments);

    Departaments find(Object id);

    Departaments getReference(Object id);

    List<Departaments> findAll();

    List<Departaments> findRange(int[] range);

    int count();

    List<Departaments> findEntity(List<String> columnNames, List<Object> values);

    Departaments getDepartamentByName(String name);

    List<Departaments> findDepartametsHavingWorkers();

    Departaments getDepartamentByChiefId(Long chiefId);

    List<Room> getRoomCollectionById(Long id);

    List<Workers> getWorkersCollectionById(Long id);
}
