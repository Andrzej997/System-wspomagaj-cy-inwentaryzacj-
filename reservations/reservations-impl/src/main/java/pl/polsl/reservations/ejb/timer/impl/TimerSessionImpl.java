package pl.polsl.reservations.ejb.timer.impl;

import java.io.Serializable;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import pl.polsl.reservations.ejb.dao.PriviligeLevelsDao;
import pl.polsl.reservations.ejb.dao.UsersDao;
import pl.polsl.reservations.ejb.timer.TimerSession;
import pl.polsl.reservations.entities.PriviligeLevels;
import pl.polsl.reservations.entities.Users;

/**
 *
 * @author wojcdeb448
 * @version 1.0
 *
 * Timer to revert users' privilege level after timeout
 */
@Singleton
public class TimerSessionImpl implements TimerSession, Serializable {

    private static final long serialVersionUID = -8590964040735192767L;

    @Resource
    private transient SessionContext context;

    @EJB
    UsersDao usersDao;
    @EJB
    PriviligeLevelsDao levelsDao;

    /**
     * Method to create timer
     *
     * @param miliseconds timeout value
     * @param userID user which creates timer
     */
    @Override
    public void createTimer(long miliseconds, int userID) {
        context.getTimerService().createTimer(miliseconds, userID);
    }

    /**
     * Method executed after timeout, reverts user privilege level which was
     * given by his chief
     *
     * @param timer instance
     */
    @Timeout
    public void revertPrivilegeLevel(Timer timer) {
        int id = (int) timer.getInfo();
        System.out.println("Revert privilege of user with id: " + id);
        Users user = usersDao.find(id);
        PriviligeLevels level = levelsDao.getPrivligeLevelsEntityByLevelValue(user.getPriviligeLevel().getPriviligeLevel() + 1);
        user.setPriviligeLevel(level);
        usersDao.edit(user);
    }
}
