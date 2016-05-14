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
public interface DepartamentsFacadeRemote extends AbstractFacadeRemote<Departaments> {

    Departaments getDepartamentByName(String name);

    List<Departaments> findDepartametsHavingWorkers();

    Departaments getDepartamentByChiefId(Long chiefId);

    List<Room> getRoomCollectionById(Long id);

    List<Workers> getWorkersCollectionById(Long id);
}
