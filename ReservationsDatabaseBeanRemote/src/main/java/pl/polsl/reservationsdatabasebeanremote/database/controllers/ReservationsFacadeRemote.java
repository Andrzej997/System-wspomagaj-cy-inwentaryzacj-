package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import java.util.List;
import javax.ejb.Remote;
import pl.polsl.reservationsdatabasebeanremote.database.Reservations;

/**
 *
 * @author matis
 */
@Remote
public interface ReservationsFacadeRemote {

    void create(Reservations reservations);

    void edit(Reservations reservations);

    void remove(Reservations reservations);
    
    void merge(Reservations reservations);

    Reservations find(Object id);

    List<Reservations> findAll();

    List<Reservations> findRange(int[] range);

    int count();
    
    public List<Reservations> findEntity(List<String> columnNames, List<Object> values);
    
}
