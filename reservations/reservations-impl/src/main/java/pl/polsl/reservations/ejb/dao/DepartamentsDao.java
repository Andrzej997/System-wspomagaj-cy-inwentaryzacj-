package pl.polsl.reservations.ejb.dao;

import java.util.List;
import javax.ejb.Local;
import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Departaments;
import pl.polsl.reservations.entities.Room;
import pl.polsl.reservations.entities.Workers;

/**
 * @author Mateusz Sojka
 * @version
 *
 * Departaments data access object inteface
 */
@Local
public interface DepartamentsDao extends AbstractDao<Departaments> {

    /**
     * Method to get departament by name
     *
     * @param name String with departament name
     * @return Departaments entity
     */
    Departaments getDepartamentByName(String name);

    /**
     * Method to find departaments which have workers assigned
     *
     * @return List of departaments
     */
    List<Departaments> findDepartametsHavingWorkers();

    /**
     * Method to get departament by his chief
     *
     * @param chiefId Long with chief id
     * @return Departaments entity
     */
    Departaments getDepartamentByChiefId(Long chiefId);

    /**
     * Method to deparaments room collection by departament id
     *
     * @param id departament id
     * @return list of rooms
     */
    List<Room> getRoomCollectionById(Long id);

    /**
     * Method to get departaments' workers by departament id
     *
     * @param id departament id
     * @return list of workers
     */
    List<Workers> getWorkersCollectionById(Long id);
}
