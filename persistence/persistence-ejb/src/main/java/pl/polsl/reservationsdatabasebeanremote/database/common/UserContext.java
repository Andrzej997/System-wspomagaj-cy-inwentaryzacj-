package pl.polsl.reservationsdatabasebeanremote.database.common;

import pl.polsl.reservationsdatabasebeanremote.database.Priviliges;

import javax.ejb.Remote;
import java.util.List;

/**
 * Created by Krzysztof Stręk on 2016-05-19.
 */
@Remote
public interface UserContext {
    void initialize(List<Priviliges> privileges);
}
