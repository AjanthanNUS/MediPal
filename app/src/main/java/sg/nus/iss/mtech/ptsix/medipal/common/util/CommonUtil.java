package sg.nus.iss.mtech.ptsix.medipal.common.util;

import android.content.Context;
import android.text.format.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.R;

/**
 * Created by win on 5/3/17.
 */

public class CommonUtil {

    public static Calendar dateToCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
    public static String formatCalender(Calendar calendar) {
        String formattedDate;
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        formattedDate = format1.format(calendar.getTime());
        return formattedDate;
    }

    public static String formatDateStandard(Date date) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormatter.format(date.getTime());
    }

    public static String date2ddMMMYYYY(Date d) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        return dateFormatter.format(d);
    }

    public static Date ddmmmyyyy2date(String ddmmmyyyy) throws ParseException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        return dateFormatter.parse(ddmmmyyyy);
    }

    public static String getDBDateTimeFormat(Date d) {
        SimpleDateFormat dbDateFormatter = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());
        return dbDateFormatter.format(d);
    }

    public static String getFormattedTime(Date d) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return timeFormatter.format(d);
    }

    public static long getMilliSeconds(int year, int month, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, days);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();

    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean checkDateBeforeToday(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date todayDate = cal.getTime();

        // // TODO: 17/3/17 Fix this to allow today onwards

        if (date.before(todayDate)) {
            return true;
        }

        return false;
    }


    public static String getFriendlyDayString(Context context, long dateInMillis) {
        // For today: "Today"
        // For tomorrow:  "Tomorrow"
        // For the next 5 days: "Wednesday" (just the day name)
        // For all days after that: "Mon Jun 8"

        Time time = new Time();
        time.setToNow();
        long currentTime = System.currentTimeMillis();
        int julianDay = Time.getJulianDay(dateInMillis, time.gmtoff);
        int currentJulianDay = Time.getJulianDay(currentTime, time.gmtoff);

        // If the date we're building the String for is today's date, the format
        // is "Today"
        if (julianDay == currentJulianDay) {
            String today = context.getString(R.string.today);

            return today + " at " + getFormattedTime(new Date(dateInMillis));
//
        } else if (julianDay < currentJulianDay + 7) {
            // If the input date is less than a week in the future, just return the day name.
            return getDayName(context, dateInMillis) + " at " + getFormattedTime(new Date(dateInMillis));
        } else {
            // Otherwise, use the form "Mon Jun 3 12:30PM"
            SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("dd MMM yyyy");
            String shortDate = shortenedDateFormat.format(dateInMillis);
            SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm a");
            shortDate = shortDate + " at " + timeformat.format(dateInMillis);
            return shortDate;
        }
    }

    public static String getFormattedMonthDay(Context context, long dateInMillis) {
        Time time = new Time();
        time.setToNow();
//        SimpleDateFormat dbDateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a");
        SimpleDateFormat monthDayFormat = new SimpleDateFormat("MMM dd");
        String monthDayString = monthDayFormat.format(dateInMillis);
        return monthDayString;
    }

    public static String getDayName(Context context, long dateInMillis) {
        // If the date is today, return the localized version of "Today" instead of the actual
        // day name.

        Time t = new Time();
        t.setToNow();
        int julianDay = Time.getJulianDay(dateInMillis, t.gmtoff);
        int currentJulianDay = Time.getJulianDay(System.currentTimeMillis(), t.gmtoff);
        if (julianDay == currentJulianDay) {
            return context.getString(R.string.today);
        } else if (julianDay == currentJulianDay + 1) {
            return context.getString(R.string.tomorrow);
        } else {
            Time time = new Time();
            time.setToNow();
            // Otherwise, the format is just the day of the week (e.g "Wednesday".
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
            return dayFormat.format(dateInMillis);
        }
    }


}
