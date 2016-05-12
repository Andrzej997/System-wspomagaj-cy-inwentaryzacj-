package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import java.util.List;
import javax.ejb.Remote;
import pl.polsl.reservationsdatabasebeanremote.database.ReservationTypes;
import pl.polsl.reservationsdatabasebeanremote.database.Reservations;

/**
 * @author matis
 */
@Remote
public interface ReservationTypesFacadeRemote {

    void setPriviligeLevel(Integer level);

    void create(ReservationTypes reservationTypes);

    void edit(ReservationTypes reservationTypes);

    void remove(Object id);

    void merge(ReservationTypes reservationTypes);

    ReservationTypes find(Object id);

    ReservationTypes getReference(Object id);

    List<ReservationTypes> findAll();

    List<ReservationTypes> findRange(int[] range);

    int count();

    public List<ReservationTypes> findEntity(List<String> columnNames, List<Object> values);

    List<Reservations> getReservationsCollectionById(Number id);
}
