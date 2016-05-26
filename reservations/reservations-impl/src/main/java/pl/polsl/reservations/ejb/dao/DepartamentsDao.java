package pl.polsl.reservations.ejb.dao;

import java.util.List;
import javax.ejb.Local;
import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Departaments;
import pl.polsl.reservations.entities.Room;
import pl.polsl.reservations.entities.Workers;

/**
 * @author matis
 */
@Local
public interface DepartamentsDao extends AbstractDao<Departaments> {

    Departaments getDepartamentByName(String name);

    List<Departaments> findDepartametsHavingWorkers();

    Departaments getDepartamentByChiefId(Long chiefId);

    List<Room> getRoomCollectionById(Long id);

    List<Workers> getWorkersCollectionById(Long id);
}
