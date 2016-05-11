package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import java.util.List;
import javax.ejb.Remote;
import pl.polsl.reservationsdatabasebeanremote.database.EqupmentState;

/**
 * @author matis
 */
@Remote
public interface EquipmentStateFacadeRemote {

    void setPriviligeLevel(Integer level);

    void create(EqupmentState equpmentState);

    void edit(EqupmentState equpmentState);

    void remove(EqupmentState equpmentState);

    void merge(EqupmentState equpmentState);

    EqupmentState find(Object id);

    EqupmentState getReference(Object id);

    List<EqupmentState> findAll();

    List<EqupmentState> findRange(int[] range);

    int count();

    public List<EqupmentState> findEntity(List<String> columnNames, List<Object> values);

}
