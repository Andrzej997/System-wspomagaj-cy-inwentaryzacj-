package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Departaments;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
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

    void remove(Workers entity);

    void merge(Workers workers);

    Workers find(Object id);

    Workers getReference(Object id);

    List<Workers> findAll();

    List<Workers> findRange(int[] range);

    int count();

    List<Workers> findEntity(List<String> columnNames, List<Object> values);

    List<Workers> getWorkersByName(String workerName);

    List<Workers> getWorkersBySurname(String surname);

    List<Workers> getWorkersByGrade(String grade);

    List<Workers> getWorkersByAdress(String adress);

    List<Workers> getWorkerByPesel(String pesel);

    List<Room> getRoomsCollectionByKeeperId(Long id);

    Departaments getDepartamentByWorkerId(Long id);

    List<Workers> getAllChiefs();

    List<Workers> getWorkersWhichHaveChief();

    List<Room> getRoomCollectionById(Number id);
}
