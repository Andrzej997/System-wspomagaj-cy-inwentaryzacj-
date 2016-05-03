package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebean.entities.RoomTypes;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author matis
 */
@Remote
public interface RoomTypesFacadeRemote {

    void setPriviligeLevel(Integer level);

    void create(RoomTypes roomTypes);

    void edit(RoomTypes roomTypes);

    void remove(RoomTypes roomTypes);

    void merge(RoomTypes roomTypes);

    RoomTypes find(Object id);

    RoomTypes getReference(Object id);

    List<RoomTypes> findAll();

    List<RoomTypes> findRange(int[] range);

    int count();

    public List<RoomTypes> findEntity(List<String> columnNames, List<Object> values);

}
