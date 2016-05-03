package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebean.entities.Reservations;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author matis
 */
@Remote
public interface ReservationsFacadeRemote {

    void setPriviligeLevel(Integer level);

    void create(Reservations reservations);

    void edit(Reservations reservations);

    void remove(Reservations reservations);

    void merge(Reservations reservations);

    Reservations find(Object id);

    Reservations getReference(Object id);

    List<Reservations> findAll();

    List<Reservations> findRange(int[] range);

    int count();

    public List<Reservations> findEntity(List<String> columnNames, List<Object> values);

}
