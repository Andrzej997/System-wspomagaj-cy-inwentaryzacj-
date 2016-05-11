package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import java.util.List;
import javax.ejb.Remote;
import pl.polsl.reservationsdatabasebeanremote.database.EquipmentType;

/**
 * @author matis
 */
@Remote
public interface EquipmentTypeFacadeRemote {

    void setPriviligeLevel(Integer level);

    void create(EquipmentType equpmentType);

    void edit(EquipmentType equpmentType);

    void remove(EquipmentType equpmentType);

    void merge(EquipmentType equpmentType);

    EquipmentType find(Object id);

    EquipmentType getReference(Object id);

    List<EquipmentType> findAll();

    List<EquipmentType> findRange(int[] range);

    int count();

    public List<EquipmentType> findEntity(List<String> columnNames, List<Object> values);

}