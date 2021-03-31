package app.control;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * DateUtils
 */
public class DateUtils {

    /**
     * diffInMinutes
     * @param date1 date1
     * @param date2 date2
     * @return Difference in minutes
     */
    public static long diffInMinutes(Date date1, Date date2) {
        return TimeUnit.MILLISECONDS.toMinutes(date2.getTime() - date1.getTime());
    }

    /**
     * extractHourFromDate
     * @param date date
     * @return hour
     */
    public static int extractHourFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * extractMinFromDate
     * @param date date
     * @return min
     */
    public static int extractMinFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * buildDate
     * @param hour hour
     * @param min min
     * @return Date
     */
    public static Date buildDate(int hour, int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(buildZeroDate());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        return calendar.getTime();
    }

    /**
     * buildZeroDate
     * @return Zero Date
     */
    public static Date buildZeroDate() {
        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR,        2021);
//        calendar.set(Calendar.MONTH,       Calendar.JANUARY);
//        calendar.set(Calendar.DATE,        1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE,      0);
        calendar.set(Calendar.SECOND,      0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
