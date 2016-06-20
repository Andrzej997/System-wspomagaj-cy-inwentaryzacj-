package pl.polsl.reservations.ejb.dao;

import java.util.List;
import javax.ejb.Local;
import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Departaments;
import pl.polsl.reservations.entities.Room;
import pl.polsl.reservations.entities.Workers;

/**
 * @author Mateusz Sojka
 * @version 1.0
 *
 * Workers data access object interface
 */
@Local
public interface WorkersDao extends AbstractDao<Workers> {

    /**
     * Method to get workers by name
     *
     * @param workerName String
     * @return list of workers
     */
    List<Workers> getWorkersByName(String workerName);

    /**
     * Method to get workers by surname
     *
     * @param surname String
     * @return list of workers
     */
    List<Workers> getWorkersBySurname(String surname);

    /**
     * Method to get workers by grade
     *
     * @param grade String
     * @return list of workers
     */
    List<Workers> getWorkersByGrade(String grade);

    /**
     * Method to get workers by address
     *
     * @param adress String
     * @return list of workers
     */
    List<Workers> getWorkersByAdress(String adress);

    /**
     * Method to get workers by pesel
     *
     * @param pesel String
     * @return list of workers
     */
    List<Workers> getWorkerByPesel(String pesel);

    /**
     * Method to get all keepers' rooms
     *
     * @param id worker id
     * @return list of rooms
     */
    List<Room> getRoomsCollectionByKeeperId(Long id);

    /**
     * Method to get departament by worker
     *
     * @param id worker id
     * @return Departaments entity
     */
    Departaments getDepartamentByWorkerId(Long id);

    /**
     * Method to get all chiefs
     *
     * @return list of workers
     */
    List<Workers> getAllChiefs();

    /**
     * Method to get workers which have chief
     *
     * @return list of workers
     */
    List<Workers> getWorkersWhichHaveChief();

    /**
     * Method to get rooms by worker id
     *
     * @param id worker id
     * @return list of rooms
     */
    List<Room> getRoomCollectionById(Number id);
}
