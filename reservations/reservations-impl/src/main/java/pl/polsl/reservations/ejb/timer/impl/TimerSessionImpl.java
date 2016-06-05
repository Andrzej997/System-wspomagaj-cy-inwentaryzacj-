package pl.polsl.reservations.ejb.timer.impl;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
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
 */
@Stateless
public class TimerSessionImpl implements TimerSession {

    @Resource
    private SessionContext context;

    @EJB
    UsersDao usersDao;
    @EJB
    PriviligeLevelsDao levelsDao;

    @Override
    public void crateTimer(long miliseconds, int userID) {
        context.getTimerService().createTimer(miliseconds, userID);
    }

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
