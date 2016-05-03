package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebean.entities.ReservationTypes;

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

    void remove(ReservationTypes reservationTypes);

    void merge(ReservationTypes reservationTypes);

    ReservationTypes find(Object id);

    ReservationTypes getReference(Object id);

    List<ReservationTypes> findAll();

    List<ReservationTypes> findRange(int[] range);

    int count();

    public List<ReservationTypes> findEntity(List<String> columnNames, List<Object> values);

}
