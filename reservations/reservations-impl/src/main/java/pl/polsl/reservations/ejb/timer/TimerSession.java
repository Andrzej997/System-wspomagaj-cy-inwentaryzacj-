package pl.polsl.reservations.ejb.timer;

import javax.ejb.Local;

/**
 *
 * @author wojcdeb448
 * @version 1.0
 * 
 * TimerSession local bean interface
 */
@Local
public interface TimerSession {

    /**
     * Method to create timer
     * 
     * @param miliseconds timeout value
     * @param userID user which creates timer
     */
    public void createTimer(long miliseconds, int userID);
}
