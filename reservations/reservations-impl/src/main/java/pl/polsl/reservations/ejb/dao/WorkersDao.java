package pl.polsl.reservations.ejb.dao;

import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Departaments;
import pl.polsl.reservations.entities.Room;
import pl.polsl.reservations.entities.Workers;

import javax.ejb.Local;
import java.util.List;

/**
 * @author matis
 */
@Local
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
