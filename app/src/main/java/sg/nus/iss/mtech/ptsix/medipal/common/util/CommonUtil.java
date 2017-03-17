package sg.nus.iss.mtech.ptsix.medipal.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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


}
