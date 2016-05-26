package pl.polsl.reservations.ejb.dao;


import java.util.List;
import javax.ejb.Local;
import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Departaments;
import pl.polsl.reservations.entities.Institutes;

/**
 * @author matis
 */
@Local
public interface InstitutesDao extends AbstractDao<Institutes> {

    Institutes getInstituteByName(String name);

    Institutes getInstituteByChiefId(Long chiefId);

    List<Departaments> getDepartamentsCollectionById(Number id);
}
