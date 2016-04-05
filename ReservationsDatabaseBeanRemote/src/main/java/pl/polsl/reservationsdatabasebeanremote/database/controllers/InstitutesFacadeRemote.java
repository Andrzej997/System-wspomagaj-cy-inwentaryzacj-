package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import java.util.List;
import javax.ejb.Remote;
import pl.polsl.reservationsdatabasebeanremote.database.Institutes;

/**
 *
 * @author matis
 */
@Remote
public interface InstitutesFacadeRemote {

    void create(Institutes institutes);

    void edit(Institutes institutes);

    void remove(Institutes institutes);
    
    void merge(Institutes institutes);

    Institutes find(Object id);

    List<Institutes> findAll();

    List<Institutes> findRange(int[] range);

    int count();
    
    public List<Institutes> findEntity(List<String> columnNames, List<Object> values);
    
}
