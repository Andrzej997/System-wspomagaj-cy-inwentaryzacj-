package pl.polsl.reservations.client.views.utils;

import java.util.Calendar;

/**
 *
 * @author matis
 */
public class DateUtils {

    public static Boolean getSemesterFromDate(Calendar date) {
        int month = date.get(Calendar.MONTH);
        if(month >= 10 || month <= 2){
            return false;
        } else {
            return true;
        }
    }

    public static Integer getWeekOfSemester(Calendar date) {
        int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
        int weekOfYear = date.get(Calendar.WEEK_OF_YEAR);
        int weekOfSemester = 1;
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);

        if (month >= 10 || month <= 2) {
            Calendar cal = date;
            cal.set(Calendar.MONTH, Calendar.OCTOBER);
            cal.set(Calendar.DATE, 1);

            if (month >= 10 && month <= 12) {
                weekOfSemester = weekOfYear - cal.get(Calendar.WEEK_OF_YEAR) + 1;
            } else {   //sprawdziæ zachowanie jak sylwester nie jest w niedzielê
                Calendar calPom = Calendar.getInstance();
                cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
                calPom.set(Calendar.YEAR, calPom.get(Calendar.YEAR) - 1);
                calPom.set(Calendar.MONTH, Calendar.DECEMBER);
                calPom.set(Calendar.DATE, 31);

                weekOfSemester = calPom.get(Calendar.WEEK_OF_YEAR) - cal.get(Calendar.WEEK_OF_YEAR);
                calPom.set(date.get(Calendar.YEAR), Calendar.JANUARY, 1);
                weekOfSemester += date.get(Calendar.WEEK_OF_YEAR) - calPom.get(Calendar.WEEK_OF_YEAR);
            }

        } else {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MONTH, Calendar.MARCH);
            cal.set(Calendar.DATE, 1);

            weekOfSemester = weekOfYear - cal.get(Calendar.WEEK_OF_YEAR) + 1;
        }

        return weekOfSemester;
    }
}
