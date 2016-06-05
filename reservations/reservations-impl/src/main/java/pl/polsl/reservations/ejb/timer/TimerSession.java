package pl.polsl.reservations.ejb.timer;

import javax.ejb.Remote;

/**
 *
 * @author wojcdeb448
 */
@Remote
public interface TimerSession {
    public void crateTimer(long miliseconds, int userID);
}
