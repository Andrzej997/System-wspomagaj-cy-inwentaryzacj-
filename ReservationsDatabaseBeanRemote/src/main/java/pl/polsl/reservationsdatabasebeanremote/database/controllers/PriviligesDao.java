package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.PriviligeLevels;
import pl.polsl.reservationsdatabasebeanremote.database.Priviliges;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author matis
 */
@Remote
public interface PriviligesDao extends AbstractDao<Priviliges> {

    List<PriviligeLevels> getPriviligeLevelsCollectionById(Number id);
}
