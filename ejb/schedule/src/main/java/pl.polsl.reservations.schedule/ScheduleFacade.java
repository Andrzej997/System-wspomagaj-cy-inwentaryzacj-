package pl.polsl.reservations.schedule;

import javax.ejb.Stateful;

/**
 * Created by Krzysztof Stręk on 2016-05-11.
 */
@Stateful(mappedName = "ScheduleFacade")
public class ScheduleFacade implements ScheduleFacadeRemote {
    public ScheduleFacade() {
    }
}
