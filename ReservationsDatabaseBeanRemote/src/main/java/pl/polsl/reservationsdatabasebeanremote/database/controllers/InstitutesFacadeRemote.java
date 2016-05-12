package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import java.util.List;
import javax.ejb.Remote;

import pl.polsl.reservationsdatabasebeanremote.database.Departaments;
import pl.polsl.reservationsdatabasebeanremote.database.Institutes;

/**
 * @author matis
 */
@Remote
public interface InstitutesFacadeRemote {

    void setPriviligeLevel(Integer level);

    void create(Institutes institutes);

    void edit(Institutes institutes);

    void remove(Object id);

    void merge(Institutes institutes);

    Institutes find(Object id);

    Institutes getReference(Object id);

    List<Institutes> findAll();

    List<Institutes> findRange(int[] range);

    int count();

    public List<Institutes> findEntity(List<String> columnNames, List<Object> values);

    Institutes getInstituteByName(String name);

    Institutes getInstituteByChiefId(Long chiefId);

    List<Departaments> getDepartamentsCollectionById(Number id);
}
