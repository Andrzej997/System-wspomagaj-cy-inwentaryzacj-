package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import java.util.List;
import javax.ejb.Remote;
import pl.polsl.reservationsdatabasebeanremote.database.ReservationTypes;

/**
 *
 * @author matis
 */
@Remote
public interface ReservationTypesFacadeRemote {

    void create(ReservationTypes reservationTypes);

    void edit(ReservationTypes reservationTypes);

    void remove(ReservationTypes reservationTypes);
    
    void merge(ReservationTypes reservationTypes);

    ReservationTypes find(Object id);

    List<ReservationTypes> findAll();

    List<ReservationTypes> findRange(int[] range);

    int count();
    
    public List<ReservationTypes> findEntity(List<String> columnNames, List<Object> values);
    
}
