package com.lucky.sweet.utility;

import java.util.Calendar;


/**
 * Created by C on 2018/3/24.
 */

public class TimeUtil {


    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();

//小时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//分钟
        int minute = calendar.get(Calendar.MINUTE);

        return hour + "时" + minute+"分";
    }
}
