package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Departaments;
import pl.polsl.reservationsdatabasebeanremote.database.Institutes;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author matis
 */
@Remote
public interface InstitutesFacadeRemote {

    void setPriviligeLevel(Integer level);

    void create(Institutes institutes);

    void edit(Institutes institutes);

    void remove(Institutes entity);

    void merge(Institutes institutes);

    Institutes find(Object id);

    Institutes getReference(Object id);

    List<Institutes> findAll();

    List<Institutes> findRange(int[] range);

    int count();

    List<Institutes> findEntity(List<String> columnNames, List<Object> values);

    Institutes getInstituteByName(String name);

    Institutes getInstituteByChiefId(Long chiefId);

    List<Departaments> getDepartamentsCollectionById(Number id);
}
