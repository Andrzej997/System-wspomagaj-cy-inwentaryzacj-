package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebean.entities.RoomSchedule;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author matis
 */
@Remote
public interface RoomScheduleFacadeRemote {

    void setPriviligeLevel(Integer level);

    void create(RoomSchedule roomSchedule);

    void edit(RoomSchedule roomSchedule);

    void remove(RoomSchedule roomSchedule);

    void merge(RoomSchedule roomSchedule);

    RoomSchedule find(Object id);

    RoomSchedule getReference(Object id);

    List<RoomSchedule> findAll();

    List<RoomSchedule> findRange(int[] range);

    int count();

    public List<RoomSchedule> findEntity(List<String> columnNames, List<Object> values);

}
