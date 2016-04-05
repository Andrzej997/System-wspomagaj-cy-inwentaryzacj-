package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import java.util.List;
import javax.ejb.Remote;
import pl.polsl.reservationsdatabasebeanremote.database.Workers;

/**
 *
 * @author matis
 */
@Remote
public interface WorkersFacadeRemote {

    void create(Workers workers);

    void edit(Workers workers);

    void remove(Workers workers);
    
    void merge(Workers workers);

    Workers find(Object id);

    List<Workers> findAll();

    List<Workers> findRange(int[] range);

    int count();
    
    public List<Workers> findEntity(List<String> columnNames, List<Object> values);
    
}
