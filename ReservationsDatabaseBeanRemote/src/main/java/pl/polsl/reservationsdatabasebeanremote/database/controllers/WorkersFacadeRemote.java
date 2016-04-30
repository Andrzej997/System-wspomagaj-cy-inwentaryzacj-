package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Workers;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author matis
 */
@Remote
public interface WorkersFacadeRemote {

    void setPriviligeLevel(Integer level);

    void create(Workers workers);

    void edit(Workers workers);

    void remove(Workers workers);

    void merge(Workers workers);

    Workers find(Object id);

    Workers getReference(Object id);

    List<Workers> findAll();

    List<Workers> findRange(int[] range);

    int count();

    public List<Workers> findEntity(List<String> columnNames, List<Object> values);

}
