package pl.polsl.reservations.schedule;

/**
 * Created by Krzysztof Stręk on 2016-05-12.
 */
public class ScheduleFactory {


    void createSchedule(ScheduleStrategy strategy, int roomId) {
        strategy.createSchedule(roomId);
    }


}
