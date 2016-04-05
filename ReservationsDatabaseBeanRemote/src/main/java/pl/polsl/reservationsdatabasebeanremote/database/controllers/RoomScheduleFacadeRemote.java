package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import java.util.List;
import javax.ejb.Remote;
import pl.polsl.reservationsdatabasebeanremote.database.RoomSchedule;

/**
 *
 * @author matis
 */
@Remote
public interface RoomScheduleFacadeRemote {

    void create(RoomSchedule roomSchedule);

    void edit(RoomSchedule roomSchedule);

    void remove(RoomSchedule roomSchedule);
    
    void merge(RoomSchedule roomSchedule);

    RoomSchedule find(Object id);

    List<RoomSchedule> findAll();

    List<RoomSchedule> findRange(int[] range);

    int count();
    
    public List<RoomSchedule> findEntity(List<String> columnNames, List<Object> values);
    
}
