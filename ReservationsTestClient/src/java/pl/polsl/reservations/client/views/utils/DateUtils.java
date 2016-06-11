package pl.polsl.reservations.client.views.utils;

import java.util.Calendar;

/**
 *
 * @author matis
 */
public class DateUtils {

    public static Boolean getSemesterFromDate(Calendar date) {
        int month = date.get(Calendar.MONTH);
        return !(month >= 10 || month <= 2);
    }

    public static Integer getWeekOfSemester(Calendar date) {
        int weekOfYear = date.get(Calendar.WEEK_OF_YEAR);
        int weekOfSemester = 1;
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
    
    /**
     * 
     * @param firstDate
     * @param secondDate
     * @return 1 firstDate > secondDate, 0 firstDate == SecondDate, -1 firstDate < secondDate
     */
    public static Integer compareDates(Calendar firstDate, Calendar secondDate){
        if(firstDate.after(secondDate)){
            return 1;
        } else if(firstDate.before(secondDate)){
            return -1;
        } else {
            return 0;
        }
    }
    
    public static Calendar cutTime(Calendar date){
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE,0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        return date;
    }
    
    public static Integer compareDatesWithoutTime(Calendar firstDate, Calendar secondDate){
        return compareDates(cutTime(firstDate), cutTime(secondDate));
    }
    
    public static Integer getYear(Calendar date){
        return date.get(Calendar.YEAR);
    }
}
