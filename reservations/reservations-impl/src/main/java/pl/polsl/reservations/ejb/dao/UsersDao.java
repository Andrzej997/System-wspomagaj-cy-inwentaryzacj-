package pl.polsl.reservations.ejb.dao;

import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.entities.Reservations;
import pl.polsl.reservations.entities.Users;
import pl.polsl.reservations.entities.Workers;

import javax.ejb.Local;
import java.util.List;

/**
 * @author matis
 */
@Local
public interface UsersDao extends AbstractDao<Users> {

    Boolean validateUser(String username, String password);

    Boolean validateUserByEmail(String email, String password);

    Long getUserPrivligeLevelByUsername(String username);

    Workers getWorkerByUsername(String username);

    Users getUserByUsername(String username);

    Users getUserByEmail(String email);

    List<Reservations> getReservationsCollectionById(Number id);
}