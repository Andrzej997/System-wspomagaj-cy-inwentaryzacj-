package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Departaments;
import pl.polsl.reservationsdatabasebeanremote.database.Institutes;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author matis
 */
@Remote
public interface InstitutesDao extends AbstractDao<Institutes> {

    Institutes getInstituteByName(String name);

    Institutes getInstituteByChiefId(Long chiefId);

    List<Departaments> getDepartamentsCollectionById(Number id);
}
