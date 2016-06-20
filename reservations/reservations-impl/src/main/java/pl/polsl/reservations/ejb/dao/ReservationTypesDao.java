package pl.polsl.reservations.ejb.dao;

import java.util.List;
import javax.ejb.Local;
import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.ReservationTypes;
import pl.polsl.reservations.entities.Reservations;

/**
 * @author Mateusz Sojka
 * @version 1.0
 * 
 * ReservationTypes data access object interface
 */
@Local
public interface ReservationTypesDao extends AbstractDao<ReservationTypes> {

    /**
     * Get reservations which has given type
     * @param id reservation type id
     * @return list of reservations
     */
    List<Reservations> getReservationsCollectionById(Number id);
}
