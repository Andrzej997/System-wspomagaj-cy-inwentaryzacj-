package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Equipment;
import pl.polsl.reservationsdatabasebeanremote.database.EquipmentType;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author matis
 */
@Remote
public interface EquipmentTypeFacadeRemote {

    void setPriviligeLevel(Integer level);

    void create(EquipmentType equpmentType);

    void edit(EquipmentType equpmentType);

    void remove(EquipmentType entity);

    void merge(EquipmentType equpmentType);

    EquipmentType find(Object id);

    EquipmentType getReference(Object id);

    List<EquipmentType> findAll();

    List<EquipmentType> findRange(int[] range);

    int count();

    List<EquipmentType> findEntity(List<String> columnNames, List<Object> values);

    List<Equipment> getEquipmentCollectionById(Number id);
}