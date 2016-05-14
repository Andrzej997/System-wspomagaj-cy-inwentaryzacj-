package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.ReservationTypes;
import pl.polsl.reservationsdatabasebeanremote.database.Reservations;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author matis
 */
@Remote
public interface ReservationTypesFacadeRemote extends AbstractFacadeRemote<ReservationTypes> {

    List<Reservations> getReservationsCollectionById(Number id);
}
