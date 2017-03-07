package sg.nus.iss.mtech.ptsix.medipal.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by win on 5/3/17.
 */

public  class CommonUtil {
    public static String formatCalender(Calendar calendar) {
        String formattedDate;

        calendar.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        formattedDate = format1.format(calendar.getTime());
        return formattedDate;
    }
}
