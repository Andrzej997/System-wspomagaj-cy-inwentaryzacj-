package pl.polsl.reservationsdatabasebeanremote.database.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.PriviligeLevels;
import pl.polsl.reservationsdatabasebeanremote.database.Priviliges;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author matis
 */
@Remote
public interface PriviligesFacadeRemote extends AbstractFacadeRemote<Priviliges> {

    List<PriviligeLevels> getPriviligeLevelsCollectionById(Number id);
}
