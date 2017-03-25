package sg.nus.iss.mtech.ptsix.medipal.common.util;

import android.content.Context;
import android.text.format.Time;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.enums.DosageEnums;

public class CommonUtil {

    public static Calendar dateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static String formatCalender(Calendar calendar) {
        String formattedDate;
        SimpleDateFormat format1 = new SimpleDateFormat(Constant.DATE_TIME_FORMAT);
        formattedDate = format1.format(calendar.getTime());
        return formattedDate;
    }

    public static String formatDateStandard(Date date) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormatter.format(date.getTime());
    }

    public static String date2ddMMMYYYY(Date d) {
        SimpleDateFormat dateFormatter = null;
        try {
            dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Constant.EMPTY_VALUE;
        }
        return dateFormatter.format(d);
    }

    public static Date ddmmmyyyy2date(String ddmmmyyyy) throws ParseException {
        SimpleDateFormat dateFormatter = null;
        try {
            dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
        return dateFormatter.parse(ddmmmyyyy);
    }

    public static String getDBDateTimeFormat(Date d) {
        SimpleDateFormat dbDateFormatter = null;
        try {
            dbDateFormatter = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Constant.EMPTY_VALUE;
        }
        return dbDateFormatter.format(d);
    }

    public static String getFormattedTime(Date d) {
        SimpleDateFormat timeFormatter = null;
        try {
            timeFormatter = new SimpleDateFormat(Constant.TIME_FORMAT, Locale.getDefault());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Constant.EMPTY_VALUE;
        }
        return timeFormatter.format(d);
    }

    public static Date convertStringToDate(String dateString, String dateFormatString) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat(dateFormatString, Locale.getDefault());
        Date formattedDate = null;
        try {
            formattedDate = timeFormatter.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
        return formattedDate;
    }

    public static String convertDateToString(Date date, String dateFormatString) {
        SimpleDateFormat dateString = null;
        try {
            dateString = new SimpleDateFormat(dateFormatString, Locale.getDefault());
        } catch (IllegalArgumentException e) {
            return null;
        }
        return dateString.format(date);
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

    public static Date getTodayDate() {
        Date date = new Date();
        return date;
    }

    public static Date getPreviousWeekDate(Date asDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(asDate);
        c.add(Calendar.DATE, -7);
        Date previousWeekDate = c.getTime();
        return previousWeekDate;
    }

    public static Date getPreviousMonthDate(Date asDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(asDate);
        c.add(Calendar.DATE, -30);
        Date previousWeekDate = c.getTime();
        return previousWeekDate;
    }

    public static final String BP_LOW = "Low";
    public static final String BP_IDEAL_PRESSURE = "Ideal blood pressure";
    public static final String BP_PRE_HIGH_PRESSURE = "Pre-high blood pressure";
    public static final String BP_HIGH_PRESSURE = "High blood pressure";

    public static Date fromDate = null;
    public static Date toDate = null;
    public static int reportType = 0;

    public static String getBPStatus(int systolic, int diastolic) {
        String ret = "";

        if ((systolic >= 70 && systolic <= 90) &&
                ((diastolic >= 40 && diastolic <= 60))) {
            ret = BP_LOW;
        } else if ((systolic >=90 && systolic <=120)
            && (diastolic >= 60 && diastolic <= 80))
        {
            ret = BP_IDEAL_PRESSURE;
        } else if ((systolic >= 120 && systolic <= 140)
                && (diastolic >= 60 && diastolic <= 80)) {
            ret = BP_PRE_HIGH_PRESSURE;
        } else if ((systolic >=140 && systolic <=190)
                && (diastolic >= 90 && diastolic <=100)){
            ret = BP_HIGH_PRESSURE;
        } else {
            ret = "Extremely Terrible";
        }
        return ret;
    }

    public static String format2Decimals(double d) {
        DecimalFormat df = new DecimalFormat("####0.00");
        return df.format(d);
    }

    public static double getBMI(int weight, int heightInCm) {
        Log.w("INFO", "Weight " + weight);
        double bmi = 0;
        double heightInmetre = ((double)heightInCm / 100);

        bmi = (weight / (heightInmetre * heightInmetre));

        Log.w("INFO ", "BMI" + bmi);
        return bmi;
    }

    public static String getBMIStatus(double bmi) {
        String status = "";
        if (bmi < 18.5) {
            status = "Underweight";
        } else if (bmi >= 18.5 && bmi <= 24.9) {
            status = "Healthy Weight";
        } else if (bmi >= 25.0 && bmi <= 29.9) {
            status = "Over Weight";
        } else {
            status = "Obese";
        }

        return status;
    }

    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

}
