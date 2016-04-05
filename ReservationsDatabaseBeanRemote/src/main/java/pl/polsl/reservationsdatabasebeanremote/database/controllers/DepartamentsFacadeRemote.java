package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import java.util.List;
import javax.ejb.Remote;
import pl.polsl.reservationsdatabasebeanremote.database.Departaments;

/**
 *
 * @author matis
 */
@Remote
public interface DepartamentsFacadeRemote {

    void create(Departaments departaments);

    void edit(Departaments departaments);

    void remove(Departaments departaments);
    
    void merge(Departaments departaments);

    Departaments find(Object id);

    List<Departaments> findAll();

    List<Departaments> findRange(int[] range);

    int count();
    
    public List<Departaments> findEntity(List<String> columnNames, List<Object> values);
    
}
