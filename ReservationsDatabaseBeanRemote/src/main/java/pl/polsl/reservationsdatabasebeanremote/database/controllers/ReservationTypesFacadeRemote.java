package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.ReservationTypes;
import pl.polsl.reservationsdatabasebeanremote.database.Reservations;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author matis
 */
@Remote
public interface ReservationTypesFacadeRemote {

    void setPriviligeLevel(Integer level);

    void create(ReservationTypes reservationTypes);

    void edit(ReservationTypes reservationTypes);

    void remove(ReservationTypes entity);

    void merge(ReservationTypes reservationTypes);

    ReservationTypes find(Object id);

    ReservationTypes getReference(Object id);

    List<ReservationTypes> findAll();

    List<ReservationTypes> findRange(int[] range);

    int count();

    List<ReservationTypes> findEntity(List<String> columnNames, List<Object> values);

    List<Reservations> getReservationsCollectionById(Number id);
}
