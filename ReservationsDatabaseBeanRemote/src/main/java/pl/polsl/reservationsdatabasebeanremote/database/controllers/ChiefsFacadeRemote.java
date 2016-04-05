package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import java.util.List;
import javax.ejb.Remote;
import pl.polsl.reservationsdatabasebeanremote.database.Chiefs;

/**
 *
 * @author matis
 */
@Remote
public interface ChiefsFacadeRemote {

    void create(Chiefs chiefs);

    void edit(Chiefs chiefs);

    void remove(Chiefs chiefs);
    
    void merge(Chiefs chiefs);

    Chiefs find(Object id);

    List<Chiefs> findAll();

    List<Chiefs> findRange(int[] range);

    int count();
    
    public List<Chiefs> findEntity(List<String> columnNames, List<Object> values);
    
}
