package pl.polsl.reservations.ejb.dao;

import java.util.List;
import javax.ejb.Local;
import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.ReservationTypes;
import pl.polsl.reservations.entities.Reservations;

/**
 * @author matis
 */
@Local
public interface ReservationTypesDao extends AbstractDao<ReservationTypes> {

    List<Reservations> getReservationsCollectionById(Number id);
}
