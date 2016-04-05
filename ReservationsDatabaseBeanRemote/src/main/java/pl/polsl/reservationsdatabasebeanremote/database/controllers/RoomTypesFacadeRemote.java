package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import java.util.List;
import javax.ejb.Remote;
import pl.polsl.reservationsdatabasebeanremote.database.RoomTypes;

/**
 *
 * @author matis
 */
@Remote
public interface RoomTypesFacadeRemote {

    void create(RoomTypes roomTypes);

    void edit(RoomTypes roomTypes);

    void remove(RoomTypes roomTypes);
    
    void merge(RoomTypes roomTypes);

    RoomTypes find(Object id);

    List<RoomTypes> findAll();

    List<RoomTypes> findRange(int[] range);

    int count();
    
    public List<RoomTypes> findEntity(List<String> columnNames, List<Object> values);
    
}
