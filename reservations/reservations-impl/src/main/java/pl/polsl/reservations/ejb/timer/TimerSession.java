package pl.polsl.reservations.ejb.timer;

import javax.ejb.Local;

/**
 *
 * @author wojcdeb448
 */
@Local
public interface TimerSession {

    public void createTimer(long miliseconds, int userID);
}
