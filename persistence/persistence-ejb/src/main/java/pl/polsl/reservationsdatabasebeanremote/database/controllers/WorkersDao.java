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
public interface WorkersDao extends AbstractDao<Workers> {

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
