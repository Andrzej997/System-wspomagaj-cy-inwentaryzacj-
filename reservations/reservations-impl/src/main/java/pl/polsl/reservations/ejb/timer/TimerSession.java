package pl.polsl.reservations.ejb.timer;

import javax.ejb.Local;

/**
 *
 * @author wojcdeb448
 */
@Local
public interface TimerSession {
    public void crateTimer(long miliseconds, int userID);
}
