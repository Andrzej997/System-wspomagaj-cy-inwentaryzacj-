package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import java.util.List;
import javax.ejb.Remote;
import pl.polsl.reservationsdatabasebeanremote.database.Priviliges;

/**
 *
 * @author matis
 */
@Remote
public interface PriviligesFacadeRemote {

    void create(Priviliges priviliges);

    void edit(Priviliges priviliges);

    void remove(Priviliges priviliges);
    
    void merge(Priviliges priviliges);

    Priviliges find(Object id);

    List<Priviliges> findAll();

    List<Priviliges> findRange(int[] range);

    int count();
    
    public List<Priviliges> findEntity(List<String> columnNames, List<Object> values);
    
}
