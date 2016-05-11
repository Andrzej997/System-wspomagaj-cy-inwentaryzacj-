package pl.polsl.reservations.schedule;

import javax.ejb.Remote;

/**
 * Created by Krzysztof Stręk on 2016-05-11.
 */
@Remote
public interface ScheduleFacadeRemote {
    default void getRoomSchedule() {};
    default void getReservationsByUser() {};
    default void createReservation() {};
    default void removeReservation() {};
    default void getReservationDetails() {};
    default void getWeeklySceduleView() {};
    default void getSemesterScheduleView() {};
    default void findReservation() {};
}
