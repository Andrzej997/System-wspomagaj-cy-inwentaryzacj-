package pl.polsl.reservations.user;

import javax.ejb.Remote;

/**
 * Created by Krzysztof Stręk on 2016-05-07.
 */
@Remote
public interface UserFacadeRemote {

    String getUser();
}
