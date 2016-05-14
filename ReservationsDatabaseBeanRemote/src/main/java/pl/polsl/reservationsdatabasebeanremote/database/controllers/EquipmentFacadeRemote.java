package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Equipment;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author matis
 */
@Remote
public interface EquipmentFacadeRemote {

    void setPriviligeLevel(Integer level);

    void create(Equipment equipment);

    void edit(Equipment equipment);

    void remove(Equipment entity);

    void merge(Equipment equipment);

    Equipment find(Object id);

    Equipment getReference(Object id);

    List<Equipment> findAll();

    List<Equipment> findRange(int[] range);

    int count();

    List<Equipment> findEntity(List<String> columnNames, List<Object> values);

    List<Equipment> getEquipmentByRoomNumber(int roomNumber);

}
