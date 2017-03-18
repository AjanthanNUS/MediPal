package sg.nus.iss.mtech.ptsix.medipal.common.util;

import android.content.Context;
import android.text.format.Time;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.enums.DosageEnums;

/**
 * Created by win on 5/3/17.
 */

public class CommonUtil {
    public static String formatCalender(Calendar calendar) {
        String formattedDate;

        calendar.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        formattedDate = format1.format(calendar.getTime());
        return formattedDate;
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

    public static boolean checkDateBeforeToday(Date date) {

        Calendar cal = Calendar.getInstance();

        cal.setTime(date);
        cal.set(Calendar.HOUR, 12);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        date = cal.getTime();

        cal.setTime(new Date());
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date todayDate = cal.getTime();

        if (date.before(todayDate)) {
            Log.d(date + "", todayDate + "");
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

    public static List<String> getDosageList() {
        List<String> dosageList = new ArrayList<>();

        dosageList.add("<Select Dosage>");
        dosageList.add(DosageEnums.PILLS.getValue(), DosageEnums.PILLS.getStringValue());
        dosageList.add(DosageEnums.CC.getValue(), DosageEnums.CC.getStringValue());
        dosageList.add(DosageEnums.ML.getValue(), DosageEnums.ML.getStringValue());
        dosageList.add(DosageEnums.GR.getValue(), DosageEnums.GR.getStringValue());
        dosageList.add(DosageEnums.MG.getValue(), DosageEnums.MG.getStringValue());
        dosageList.add(DosageEnums.DROPS.getValue(), DosageEnums.DROPS.getStringValue());
        dosageList.add(DosageEnums.PIECES.getValue(), DosageEnums.PIECES.getStringValue());
        dosageList.add(DosageEnums.PUFFS.getValue(), DosageEnums.PUFFS.getStringValue());
        dosageList.add(DosageEnums.UNITS.getValue(), DosageEnums.UNITS.getStringValue());
        dosageList.add(DosageEnums.TEASPOON.getValue(), DosageEnums.TEASPOON.getStringValue());
        dosageList.add(DosageEnums.TABLESPOON.getValue(), DosageEnums.TABLESPOON.getStringValue());
        dosageList.add(DosageEnums.PATCH.getValue(), DosageEnums.PATCH.getStringValue());
        dosageList.add(DosageEnums.MCG.getValue(), DosageEnums.MCG.getStringValue());
        dosageList.add(DosageEnums.L.getValue(), DosageEnums.L.getStringValue());
        dosageList.add(DosageEnums.MEQ.getValue(), DosageEnums.MEQ.getStringValue());
        dosageList.add(DosageEnums.SPRAY.getValue(), DosageEnums.SPRAY.getStringValue());

        return dosageList;
    }

}
