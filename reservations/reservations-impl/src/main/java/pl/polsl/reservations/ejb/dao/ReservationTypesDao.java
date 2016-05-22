package pl.polsl.reservations.ejb.dao;

import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.ReservationTypes;
import pl.polsl.reservations.entities.Reservations;

import javax.ejb.Local;
import java.util.List;

/**
 * @author matis
 */
@Local
public interface ReservationTypesDao extends AbstractDao<ReservationTypes> {

    List<Reservations> getReservationsCollectionById(Number id);
}
